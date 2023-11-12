package com.soft2242.shop.service.impl;

import com.aliyun.oss.ServiceException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.soft2242.shop.common.exception.ServerException;
import com.soft2242.shop.entity.UserShippingAddress;
import com.soft2242.shop.convert.AddressConvert;
import com.soft2242.shop.enums.AddressDefaultEnum;
import com.soft2242.shop.mapper.UserShippingAddressMapper;
import com.soft2242.shop.service.RedisService;
import com.soft2242.shop.vo.AddressVO;
import com.soft2242.shop.service.UserShippingAddressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ycshang
 * @since 2023-11-07
 */
@Service
@AllArgsConstructor

public class UserShippingAddressServiceImpl extends ServiceImpl<UserShippingAddressMapper, UserShippingAddress> implements UserShippingAddressService {
    @Resource
    private RedisService redisService;
    private final UserShippingAddressMapper userShippingAddressMapper;

    @Override
    public Integer saveShippingAddress(AddressVO addressVO) {
        UserShippingAddress convert = AddressConvert.INSTANCE.convert(addressVO);
        if (addressVO.getIsDefault() == AddressDefaultEnum.DEFAULT_ADDRESS.getValue()) {

            List<UserShippingAddress> list = userShippingAddressMapper.selectList(new
                    LambdaQueryWrapper<UserShippingAddress>().eq(UserShippingAddress::getIsDefault,
                    AddressDefaultEnum.DEFAULT_ADDRESS.getValue()));
            if (list.size() > 0) {
                throw new ServerException("已经存在默认地址，请勿重复操作");
            }
        }
        save(convert);
        return convert.getId();

    }

    /**
     * 收货地址列表
     *
     * @param addressVO
     * @return TODO @RequestBody @Validated Query query 分页查询
     * 参考：GoodsController  ，GoodsServiceImpl
     */
    @Override
    public List<AddressVO> getAddressList(AddressVO addressVO) {
        LambdaQueryWrapper<UserShippingAddress> wrapper = new LambdaQueryWrapper<>();
        wrapper
                .eq(UserShippingAddress::getUserId, addressVO.getUserId())
                .eq(UserShippingAddress::getDeleteFlag, 0);
        List<UserShippingAddress> userShippingAddresses = baseMapper.selectList(wrapper);
        List<AddressVO> addressVOS = AddressConvert.INSTANCE.convertToAddressVOList(userShippingAddresses);
        return addressVOS;
    }

    /**
     * 收货地址详情
     * 被删除了则提示不存在
     *
     * @param
     * @return
     */
    @Override
    public AddressVO getAddressDetail(Integer addressId) {
//        被删除了则提示不存在
        LambdaQueryWrapper<UserShippingAddress> wrapper = new LambdaQueryWrapper<>();

        wrapper.eq(UserShippingAddress::getDeleteFlag, 0)
                .eq(UserShippingAddress::getId, addressId);
        UserShippingAddress address = userShippingAddressMapper.selectOne(wrapper);
        if (address == null) {
            throw new ServiceException("该地址不存在，可能被删除");
        }
        AddressVO vo = AddressConvert.INSTANCE.convertToAddressVO(address);
        return vo;
    }

    /**
     * 修改收货地址
     *
     * @param addressVO
     * @return
     */
    @Override
    public Integer updateAddress(AddressVO addressVO) {
//        判断是否存在
        LambdaQueryWrapper<UserShippingAddress> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserShippingAddress::getDeleteFlag, 0)
                .eq(UserShippingAddress::getId, addressVO.getId());
        UserShippingAddress one = userShippingAddressMapper.selectOne(wrapper);
        if (one == null) {
            throw new ServiceException("该地址不存在，可能被删除");
        }
//        获取用户输入数据
        UserShippingAddress userShippingAddress = AddressConvert.INSTANCE.convert(addressVO);
        //判断其他参数。。。。
        //TODO 在convert 配置参数判断

//        如果更新成功，该整数值会是大于 0 的，表示实际被更新的记录行数。
        int update = userShippingAddressMapper.update(userShippingAddress, wrapper);

        return update;
    }

    /**
     * 删除收货地址
     * 使用逻辑删除
     *
     * @param
     * @return
     */
    @Override
    public Integer delAddress(Integer addressId) {
        //        判断是否存在
        LambdaQueryWrapper<UserShippingAddress> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserShippingAddress::getDeleteFlag, 0)
                .eq(UserShippingAddress::getId, addressId);
        UserShippingAddress back = userShippingAddressMapper.selectOne(wrapper);
        if (back == null) {
            throw new RuntimeException("该地址不存在,检查参数是否合规");
        }
        UserShippingAddress build = UserShippingAddress.builder()
                .id(back.getId())
                .userId(back.getUserId())
                .receiver(back.getReceiver())
                .contact(back.getContact())
                .provinceCode(back.getProvinceCode())
                .cityCode(back.getCityCode())
                .address(back.getAddress())
                .isDefault(back.getIsDefault())
                .deleteFlag(1)
                .createTime(back.getCreateTime())
                .updateTime(LocalDateTime.now()).build();


//        如果更新成功，该整数值会是大于 0 的，表示实际被更新的记录行数。
//        LambdaQueryWrapper<UserShippingAddress> updateWrapper = new LambdaQueryWrapper<>();
//        updateWrapper.eq(UserShippingAddress::getId,addressId);
        int del = userShippingAddressMapper.updateAddressByIdInteger(build);

        return del;

    }
}

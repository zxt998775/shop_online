package com.soft2242.shop.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.soft2242.shop.entity.UserShippingAddress;
import com.baomidou.mybatisplus.extension.service.IService;
import com.soft2242.shop.vo.AddressVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ycshang
 * @since 2023-11-07
 */
public interface UserShippingAddressService extends IService<UserShippingAddress> {

    Integer saveShippingAddress(AddressVO addressVO);

 List<AddressVO> getAddressList(AddressVO addressVO);
 AddressVO getAddressDetail(Integer addressId);
 Integer updateAddress(AddressVO addressVO) ;
 Integer delAddress(Integer addressId) ;


}

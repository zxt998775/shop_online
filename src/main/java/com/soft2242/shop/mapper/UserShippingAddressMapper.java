package com.soft2242.shop.mapper;

import com.soft2242.shop.entity.UserShippingAddress;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ycshang
 * @since 2023-11-07
 */
public interface UserShippingAddressMapper extends BaseMapper<UserShippingAddress> {

    Integer updateAddressByIdInteger(UserShippingAddress userShippingAddress);

}

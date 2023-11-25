package com.soft2242.shop.convert;

import com.soft2242.shop.entity.UserOrder;
import com.soft2242.shop.vo.OrderDetailVO;
//你他妈记住了 ，这个包
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserOrderDetailConvert {


    UserOrderDetailConvert INSTANCE = Mappers.getMapper(UserOrderDetailConvert.class);





    OrderDetailVO convertToOrderDetailVo(UserOrder userOrder);
}


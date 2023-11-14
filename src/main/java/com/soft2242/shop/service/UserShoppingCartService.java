package com.soft2242.shop.service;

import com.soft2242.shop.entity.UserShoppingCart;
import com.baomidou.mybatisplus.extension.service.IService;
import com.soft2242.shop.query.CartQuery;
import com.soft2242.shop.query.EditCartQuery;
import com.soft2242.shop.vo.CartGoodsVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author sunyu
 * @since 2023-11-08
 */
public interface UserShoppingCartService extends IService<UserShoppingCart> {

    //添加购物车
    CartGoodsVO addShopCart(CartQuery query);

    //购物车列表
    List<CartGoodsVO> shopCartList(Integer userId);

    //修改购物车
    CartGoodsVO editCart(EditCartQuery query);

    //删除/清空购物车单品
    void removeCartGoods(Integer userId,List<Integer> ids);

    //购物车全选/取消全选
    void editCartSelected(Boolean selected, Integer userId);

}

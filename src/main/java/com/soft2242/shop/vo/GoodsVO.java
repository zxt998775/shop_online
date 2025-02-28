package com.soft2242.shop.vo;

import com.soft2242.shop.entity.GoodsDetail;
import com.soft2242.shop.entity.GoodsSpecification;
import com.soft2242.shop.entity.GoodsSpecificationDetail;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class GoodsVO {
    @Schema(description = "主键id")
    private String id;
    @Schema(description = "商品名称")
    private String name;
    @Schema(description = "商品描述")
    private String description;
    @Schema(description = "商品价格")
    private Double price;
    @Schema(description = "原价")
    private Double oldPrice;
    @Schema(description = "折扣价格")
    private Double discount;
    @Schema(description = "库存")
    private Integer inventory;
    @Schema(description = "销售额")
    private Integer salesCount;
    @Schema(description = "商品详情页主图")
    private List<String> mainPictures;
    @Schema(description = "商品属性列表")
//    private List<GoodsSpecification> specs;
//    用户商品模块json转集合类，影响的话建意该成 mybatis handle 自动转换
    private List<GoodsSpecificationVO> specs;
    @Schema(description = "商品属性")
//    private List<GoodsSpecificationDetail> skus;
    //    用户商品模块json转集合类，影响的话建意该成 mybatis handle 自动转换

    private List<GoodsSpecificationDetailVO> skus;
    @Schema(description = "商品详情")
    private List<String> productPictures;
    @Schema(description = "商品规格")
    private List<GoodsDetail> properties;
    @Schema(description = "商品推荐列表")
    private List<RecommendGoodsVO> similarProducts;
}

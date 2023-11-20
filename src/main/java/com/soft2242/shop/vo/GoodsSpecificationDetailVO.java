package com.soft2242.shop.vo;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class GoodsSpecificationDetailVO {


    private Integer id;


    private String goodsId;


    private String cover;


    private Integer inventory;


    private Double price;

    private Double oldPrice;


    private List<SpecsVO> specs;


    private Integer deleteFlag;

    private LocalDateTime createTime;


    private LocalDateTime updateTime;
}

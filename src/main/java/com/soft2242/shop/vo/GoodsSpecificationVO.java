package com.soft2242.shop.vo;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.mapstruct.control.NoComplexMapping;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoComplexMapping
@AllArgsConstructor
@Builder
public class GoodsSpecificationVO {


    private Integer id;


    private String goodsId;


    private String name;



    private List<GoodsValueVO> value;


    private Integer deleteFlag;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}

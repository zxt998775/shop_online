package com.soft2242.shop.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GoodsValueVO {
    private String desc;
    private String name;
    private String picture;
}

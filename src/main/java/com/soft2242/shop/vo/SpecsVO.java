package com.soft2242.shop.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SpecsVO {
//    @JsonProperty("name")
    private String name;
//    @JsonProperty("valueName")
    private String valueName;
}

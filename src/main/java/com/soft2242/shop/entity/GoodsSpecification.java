package com.soft2242.shop.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.util.List;

import com.soft2242.shop.vo.GoodsValueVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author ycshang
 * @since 2023-11-07
 */
@Getter
@Setter
@TableName("t_goods_specification")
@ApiModel(value = "GoodsSpecification对象", description = "")
public class GoodsSpecification {

    @ApiModelProperty("主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("商品id")
    @TableField("goods_id")
    private String goodsId;

    @ApiModelProperty("规格名称")
    @TableField("name")
    private String name;

    @ApiModelProperty("属性详情")
    @TableField("value")
    private String value;
//    private List<GoodsValueVO> value;

    @ApiModelProperty("逻辑删除(0-未删除，1已删除)")
    @TableField("delete_flag")
    @TableLogic
    private Integer deleteFlag;

    @ApiModelProperty("创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}

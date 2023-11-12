package com.soft2242.shop.controller;

import com.soft2242.shop.common.result.Result;
import com.soft2242.shop.service.GoodsService;
import com.soft2242.shop.vo.GoodsVO;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ycshang
 * @since 2023-11-07
 */
@RestController
@RequestMapping("/shop/goodsDetail")
public class GoodsDetailController {

    @Resource
    GoodsService goodsService;


    @Operation(summary = "首页-商品详情")
    @GetMapping("detail")
    public Result<GoodsVO> getGoodsDetail(@RequestParam Integer id) {
        GoodsVO goodsDetail = goodsService.getGoodsDetail(id);
        return Result.ok(goodsDetail);
    }


}

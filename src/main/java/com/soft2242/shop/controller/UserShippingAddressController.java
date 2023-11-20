package com.soft2242.shop.controller;

import com.soft2242.shop.common.result.Result;
import com.soft2242.shop.vo.AddressVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import com.soft2242.shop.service.UserShippingAddressService;

import java.util.List;

import static com.soft2242.shop.common.utils.ObtainUserIdUtils.getUserId;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ycshang
 * @since 2023-11-07
 */

@Tag(name = "地址管理")
@RestController
@RequestMapping("/member")
@AllArgsConstructor
public class UserShippingAddressController {
    private UserShippingAddressService userShippingAddressService;


    @Operation(summary = "添加收货地址")
    @PostMapping("/address")
    public Result<Integer> saveAddress(@RequestBody @Validated AddressVO addressVO, HttpServletRequest request) {
        Integer userId = getUserId(request);
        addressVO.setUserId(userId);
        Integer addressId = userShippingAddressService.saveShippingAddress(addressVO);
        return Result.ok(addressId);
    }

    @Operation(summary = "收货地址列表")
    @GetMapping("/list")
    public Result<List<AddressVO>> getAddressList(HttpServletRequest request) {
        Integer userId = getUserId(request);
        List<AddressVO> addressList = userShippingAddressService.getAddressList(AddressVO.builder().userId(userId).build());
        return Result.ok(addressList);
    }

    @Operation(summary = "修改收货地址")
    @PostMapping("/updateAddress")
    public Result<String> updateAddress(@RequestBody @Validated AddressVO addressVO,@Parameter Integer id ) {
//        addressVO.setId(id);
        Integer integer = userShippingAddressService.updateAddress(addressVO);
        if (integer > 0)
            return Result.ok("修改成功");
        else
            return Result.error("修改失败");

    }

    @Operation(summary = "删除收货地址")
    @PostMapping("/delAddress")
    public Result<String> delAddress(@RequestParam(value = "id") Integer addressId) {

        Integer integer = userShippingAddressService.delAddress(addressId);
        if (integer > 0)
            return Result.ok("删除成功");
        else
            return Result.error("删除失败");
    }

    @Operation(summary = "收货地址详情")
    @GetMapping("/address/detail")
    public Result<AddressVO> getAddressDetail(@RequestParam(value = "id") Integer addressId, HttpServletRequest request) {
        Integer userId = getUserId(request);
        AddressVO detail = userShippingAddressService.getAddressDetail(addressId);
        return Result.ok(detail);
    }


}




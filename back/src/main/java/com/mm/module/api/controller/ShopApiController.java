package com.mm.module.api.controller;

import com.mm.common.APIResult;
import com.mm.common.errorcode.ErrorCode;
import com.mm.domain.model.Shop;
import com.mm.domain.pojo.CityDTO;
import com.mm.domain.pojo.ShopDTO;
import com.mm.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.mm.common.APIResult.*;
import static com.mm.common.errorcode.ShopErrorCode.ADD_SHOP_FAIL;
import static com.mm.common.errorcode.ShopErrorCode.DELETE_SHOP_FAIL;
import static com.mm.common.errorcode.ShopErrorCode.UPDATE_SHOP_FAIL;

@RestController
@RequestMapping("/api/shop")
public class ShopApiController {

    @Autowired
    ShopService shopService;

    @GetMapping("/search")
    public APIResult<List<Shop>> search(@RequestParam(value = "name", required = false) String name,
                                        @RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
                                        @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        List<Shop> result = shopService.find(name, pageNo, pageSize);
        return withCollectionIfEmpty(result).build();
    }

    @PostMapping("/add")
    public APIResult<String> add(@RequestBody @Valid ShopDTO shopDTO) {
        return withBoolean(shopService.add(shopDTO), ADD_SHOP_FAIL).build();
    }

    @PostMapping("/update")
    public APIResult<String> update(@RequestBody @Valid ShopDTO shopDTO) {
        return withBoolean(shopService.update(shopDTO), UPDATE_SHOP_FAIL).build();
    }

    @PostMapping("/del")
    public APIResult<String> del(@RequestBody @Valid ShopDTO shopDTO) {
        return withBoolean(shopService.del(shopDTO), DELETE_SHOP_FAIL).build();
    }

    @GetMapping("/detail")
    public APIResult<List> detail(@RequestParam(value = "id") Integer id) {
        return withNonNull(shopService.detail(id), ErrorCode.EMPTY_DATA).build();
    }
}

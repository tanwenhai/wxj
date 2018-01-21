package com.mm.module.api.controller;

import com.mm.common.APIResult;
import com.mm.common.errorcode.ErrorCode;
import com.mm.domain.model.City;
import com.mm.domain.pojo.CategoryDTO;
import com.mm.domain.pojo.CityDTO;
import com.mm.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.mm.common.APIResult.withBoolean;
import static com.mm.common.APIResult.withCollectionIfEmpty;
import static com.mm.common.APIResult.withNonNull;
import static com.mm.common.errorcode.CategoryErrorCode.ADD_CATEGORY_FAIL;
import static com.mm.common.errorcode.CategoryErrorCode.DELETE_CATEGORY_FAIL;
import static com.mm.common.errorcode.CategoryErrorCode.UPDATE_CATEGORY_FAIL;
import static com.mm.common.errorcode.CityErrorCode.ADD_CITY_FAIL;
import static com.mm.common.errorcode.CityErrorCode.DELETE_CITY_FAIL;
import static com.mm.common.errorcode.CityErrorCode.UPDATE_CITY_FAIL;

/**
 * @author tanwenhai@gusoftware.com
 */
@RestController
@RequestMapping("/api/city")
public class CityApiController {

    @Autowired
    CityService cityService;

    @GetMapping("/search")
    public APIResult<List<City>> search(@RequestParam(value = "name", required = false) String name,
                                        @RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
                                        @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        List<City> result = cityService.find(name, pageNo, pageSize);
        return withCollectionIfEmpty(result).build();
    }

    @PostMapping("/add")
    public APIResult<String> add(@RequestBody @Valid CityDTO cityDTO) {
        return withBoolean(cityService.add(cityDTO), ADD_CITY_FAIL).build();
    }

    @PostMapping("/update")
    public APIResult<String> update(@RequestBody @Valid CityDTO cityDTO) {
        return withBoolean(cityService.update(cityDTO), UPDATE_CITY_FAIL).build();
    }

    @PostMapping("/del")
    public APIResult<String> del(@RequestBody @Valid CityDTO cityDTO) {
        return withBoolean(cityService.del(cityDTO), DELETE_CITY_FAIL).build();
    }

    @GetMapping("/detail")
    public APIResult<List> detail(@RequestParam(value = "id") Integer id) {
        return withNonNull(cityService.detail(id), ErrorCode.EMPTY_DATA).build();
    }
}

package com.mm.module.api.controller;

import com.mm.common.APIResult;
import com.mm.common.errorcode.ErrorCode;
import com.mm.domain.pojo.CategoryDTO;
import com.mm.service.CategoryService;
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

/**
 * @author tanwenhai@gusoftware.com
 */
@RestController
@RequestMapping("/api/category")
public class CategoryApiController {

    @Autowired
    CategoryService categoryService;

    /**
     * 添加分类
     * @param categoryDTO
     * @return
     */
    @PostMapping("/add")
    public APIResult<String> add(@RequestBody @Valid CategoryDTO categoryDTO) {
        return withBoolean(categoryService.add(categoryDTO), ADD_CATEGORY_FAIL).build();
    }

    @PostMapping("/update")
    public APIResult<String> update(@RequestBody @Valid CategoryDTO categoryDTO) {
        return withBoolean(categoryService.update(categoryDTO), UPDATE_CATEGORY_FAIL).build();
    }

    @PostMapping("/del")
    public APIResult<String> del(@RequestBody @Valid CategoryDTO categoryDTO) {
        return withBoolean(categoryService.del(categoryDTO), DELETE_CATEGORY_FAIL).build();
    }

    @GetMapping("/search")
    public APIResult<List> search(@RequestParam(value = "name", required = false) String name,
                                  @RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
                                  @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        return withCollectionIfEmpty(categoryService.search(name, pageNo, pageSize)).build();
    }

    @GetMapping("/detail")
    public APIResult<List> detail(@RequestParam(value = "id") Integer id) {
        return withNonNull(categoryService.detail(id), ErrorCode.EMPTY_DATA).build();
    }
}

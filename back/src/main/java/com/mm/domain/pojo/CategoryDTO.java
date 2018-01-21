package com.mm.domain.pojo;

import com.mm.domain.model.CategoryCity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author tanwenhai@gusoftware.com
 */
@Setter
@Getter
public class CategoryDTO {
    private Integer id;
    private String name;
    private Integer sort;
    private List<Integer> cityList;

    public List<CategoryCity> toCategoryCityList() {
        return cityList.stream()
                .map(cityId -> createCategoryCity(id, cityId))
                .collect(toList());
    }

    private CategoryCity createCategoryCity(Integer categoryId, Integer cityId) {
        CategoryCity categoryCity = new CategoryCity();
        categoryCity.setCategoryId(categoryId);
        categoryCity.setCityId(cityId);
        return categoryCity;
    }
}

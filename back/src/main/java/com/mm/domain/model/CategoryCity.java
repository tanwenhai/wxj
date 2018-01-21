package com.mm.domain.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CategoryCity extends BaseModel {
    private Integer id;
    private Integer categoryId;
    private Integer cityId;
}

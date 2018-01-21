package com.mm.domain.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CityShop extends BaseModel {
    private Integer id;
    private Integer cityId;
    private Integer shopId;
}

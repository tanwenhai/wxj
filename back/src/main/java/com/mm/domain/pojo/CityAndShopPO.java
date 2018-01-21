package com.mm.domain.pojo;

import com.mm.domain.model.City;
import com.mm.domain.model.Shop;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * @author tanwenhai@gusoftware.com
 */
@Setter
@Getter
public class CityAndShopPO {
    private Integer id;
    private String name;
    private Set<Shop> shopList;
}

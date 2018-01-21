package com.mm.domain.pojo;

import com.mm.domain.model.CityShop;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Setter
@Getter
public class CityDTO {
    private Integer id;
    private String name;
    private List<Integer> shopList;

    public List<CityShop> toCityShopList() {
        return shopList.stream()
                .map(shopId -> createCityShop(id, shopId))
                .collect(toList());
    }

    private CityShop createCityShop(Integer cityId, Integer shopId) {
        CityShop cityShop = new CityShop();
        cityShop.setCityId(cityId);
        cityShop.setShopId(shopId);
        return cityShop;
    }
}

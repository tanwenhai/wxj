package com.mm.domain.pojo;

import com.mm.domain.model.City;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * @author tanwenhai@gusoftware.com
 */
@Setter
@Getter
public class CategoryAndCityPO {
    private Integer id;
    private String name;
    private Integer sort;
    private Set<City> citys;
}

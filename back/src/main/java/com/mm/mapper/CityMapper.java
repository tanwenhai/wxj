package com.mm.mapper;

import com.mm.domain.model.City;
import com.mm.domain.pojo.CityAndShopPO;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author tanwenhai@gusoftware.com
 */
@Mapper
public interface CityMapper {

    List<City> find(@Param("name") String name);

    int insert(City city);

    @Update("UPDATE city SET name = #{name} WHERE id = #{id}")
    int update(City city);

    @Delete("DELETE FROM city WHERE id = #{id}")
    int deleteById(@Param("id") Integer id);

    CityAndShopPO findCityAndShopById(Integer id);
}

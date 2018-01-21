package com.mm.mapper;

import com.mm.domain.model.CityShop;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CityShopMapper {
    int batchInsert(@Param("records") List<CityShop> cityShops);

    @Delete("DELETE FROM city_shop WHERE city_id = #{id}")
    int deleteByCategoryId(@Param("id") Integer id);
}

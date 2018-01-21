package com.mm.mapper;

import com.mm.domain.model.CategoryCity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CategoryCityMapper {
    int batchInsert(@Param("records") List<CategoryCity> categoryCities);

    @Delete("DELETE FROM category_city WHERE category_id = #{id}")
    int deleteByCategoryId(@Param("id") Integer id);
}

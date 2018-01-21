package com.mm.mapper;

import com.mm.domain.model.Category;
import com.mm.domain.pojo.CategoryAndCityPO;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author tanwenhai@gusoftware.com
 */
@Mapper
public interface CategoryMapper {

    int insert(Category category);

    List<CategoryAndCityPO> findCategoryWithCity(@Param("name") String name);

    CategoryAndCityPO findCategoryWithCityById(@Param("id") Integer id);

    @Update("UPDATE category SET name = #{name}, sort = #{sort} WHERE id = #{id}")
    int update(Category category);

    @Delete("DELETE FROM category WHERE id = #{id}")
    int deleteById(@Param("id") Integer id);
}

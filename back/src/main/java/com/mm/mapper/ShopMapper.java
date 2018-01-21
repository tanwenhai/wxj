package com.mm.mapper;

import com.mm.domain.model.City;
import com.mm.domain.model.Shop;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ShopMapper {
    List<City> find(@Param("name") String name);

    @Insert("INSERT INTO shop(`name`, `img_url`, `content`) VALUE (#{name}, #{imgUrl}, #{content})")
    int insert(Shop shop);

    @Update("UPDATE shop SET name = #{name}, img_url = #{imgUrl}, content = #{content} WHERE id = #{id}")
    int update(Shop shop);

    @Delete("DELTE FROM shop WHERE id = #{id}")
    int deleteById(@Param("id") Integer id);

    Shop findOneById(@Param("id") Integer id);
}

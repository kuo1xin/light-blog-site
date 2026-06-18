package com.lightblog.mapper;

import com.lightblog.entity.Category;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface CategoryMapper {
    @Select("select category_id as id, category_name as name, user_id as userId, create_time as createTime from category where user_id = #{userId} order by create_time desc")
    List<Category> listByUser(@Param("userId") Long userId);

    @Select("select category_id as id, category_name as name, user_id as userId, create_time as createTime from category where category_id = #{id}")
    Category selectById(@Param("id") Long id);

    @Insert("insert into category (category_name, user_id, create_time) values (#{name}, #{userId}, now())")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "category_id")
    int insert(Category category);

    @Update("update category set category_name = #{name} where category_id = #{id} and user_id = #{userId}")
    int update(Category category);

    @Delete("delete from category where category_id = #{id} and user_id = #{userId}")
    int deleteById(@Param("id") Long id, @Param("userId") Long userId);
}

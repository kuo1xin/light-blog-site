package com.lightblog.mapper;

import com.lightblog.entity.BlogSpace;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface BlogSpaceMapper {
    @Select("select user_id as userId, name, intro, update_time as updateTime from blog_space where user_id = #{userId}")
    BlogSpace selectByUserId(@Param("userId") Long userId);

    @Insert("insert into blog_space (user_id, name, intro, update_time) values (#{userId}, #{name}, #{intro}, now())")
    int insert(BlogSpace space);

    @Update("update blog_space set name = #{name}, intro = #{intro}, update_time = now() where user_id = #{userId}")
    int update(BlogSpace space);
}

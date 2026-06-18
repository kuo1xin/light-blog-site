package com.lightblog.mapper;

import com.lightblog.entity.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface UserMapper {
    @Select("select user_id as id, username, password, nickname, email, avatar, intro, role, status, create_time as createTime from `user` where username = #{username}")
    User selectByUsername(@Param("username") String username);

    @Select("select user_id as id, username, password, nickname, email, avatar, intro, role, status, create_time as createTime from `user` where user_id = #{id}")
    User selectById(@Param("id") Long id);

    @Select("select user_id as id, username, password, nickname, email, avatar, intro, role, status, create_time as createTime from `user` order by create_time desc")
    List<User> listAll();

    @Insert("insert into `user` (username, password, nickname, email, avatar, intro, role, status, create_time) values (#{username}, #{password}, #{nickname}, #{email}, #{avatar}, #{intro}, #{role}, #{status}, now())")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "user_id")
    int insert(User user);

    @Update("update `user` set nickname = #{nickname}, email = #{email}, avatar = #{avatar}, intro = #{intro} where user_id = #{id}")
    int updateProfile(User user);

    @Update("update `user` set password = #{password} where user_id = #{id}")
    int updatePassword(@Param("id") Long id, @Param("password") String password);

    @Update("update `user` set status = #{status}, nickname = #{nickname}, email = #{email}, intro = #{intro}, role = #{role} where user_id = #{id}")
    int updateAdmin(User user);

    @Delete("delete from `user` where user_id = #{id} and role <> 'admin'")
    int deleteNonAdmin(@Param("id") Long id);
}

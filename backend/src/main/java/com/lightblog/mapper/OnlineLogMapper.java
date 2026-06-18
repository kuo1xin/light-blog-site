package com.lightblog.mapper;

import com.lightblog.entity.OnlineLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface OnlineLogMapper {
    @Insert("insert into online_log (user_id, login_time, last_active, status) values (#{userId}, now(), now(), 1)")
    int insertLogin(@Param("userId") Long userId);

    @Update("update online_log set last_active = now(), status = 1 where user_id = #{userId} and status = 1")
    int touch(@Param("userId") Long userId);

    @Update("update online_log set status = 0, last_active = now() where user_id = #{userId} and status = 1")
    int logout(@Param("userId") Long userId);

    @Select("select l.log_id as id, l.user_id as userId, u.username, l.login_time as loginTime, l.last_active as lastActive, l.status from online_log l join `user` u on l.user_id = u.user_id where l.status = 1 order by l.last_active desc")
    List<OnlineLog> listOnline();
}

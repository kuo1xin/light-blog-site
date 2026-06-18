package com.lightblog.mapper;

import com.lightblog.entity.Follow;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface FollowMapper {
    @Select("select follow_id as id, from_uid as fromUid, to_uid as toUid, article_id as articleId, type, create_time as createTime from follow where from_uid = #{fromUid} and type = #{type} and ((type = 'user' and to_uid = #{targetId}) or (type = 'article' and article_id = #{targetId}))")
    Follow find(@Param("fromUid") Long fromUid, @Param("type") String type, @Param("targetId") Long targetId);

    @Insert("insert into follow (from_uid, to_uid, article_id, type, create_time) values (#{fromUid}, #{toUid}, #{articleId}, #{type}, now())")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "follow_id")
    int insert(Follow follow);

    @Delete("delete from follow where follow_id = #{id}")
    int deleteById(@Param("id") Long id);

    @Select("select f.follow_id as id, f.from_uid as fromUid, f.to_uid as toUid, f.article_id as articleId, f.type, case when f.type = 'user' then u.nickname else a.title end as targetName, f.create_time as createTime from follow f left join `user` u on f.to_uid = u.user_id left join article a on f.article_id = a.article_id where f.from_uid = #{userId} order by f.create_time desc")
    List<Follow> listByUser(@Param("userId") Long userId);

    @Select("select count(*) from follow where from_uid = #{userId}")
    long countByUser(@Param("userId") Long userId);
}

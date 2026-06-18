package com.lightblog.mapper;

import com.lightblog.entity.Comment;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CommentMapper {
    @Select("select cm.comment_id as id, cm.article_id as articleId, a.title as articleTitle, cm.user_id as userId, u.nickname as nickname, cm.content, cm.create_time as createTime from `comment` cm join `user` u on cm.user_id = u.user_id join article a on cm.article_id = a.article_id where cm.article_id = #{articleId} order by cm.create_time desc")
    List<Comment> listByArticle(@Param("articleId") Long articleId);

    @Select("select cm.comment_id as id, cm.article_id as articleId, a.title as articleTitle, cm.user_id as userId, u.nickname as nickname, cm.content, cm.create_time as createTime from `comment` cm join `user` u on cm.user_id = u.user_id join article a on cm.article_id = a.article_id where cm.comment_id = #{id}")
    Comment selectById(@Param("id") Long id);

    @Select("select cm.comment_id as id, cm.article_id as articleId, a.title as articleTitle, cm.user_id as userId, u.nickname as nickname, cm.content, cm.create_time as createTime from `comment` cm join `user` u on cm.user_id = u.user_id join article a on cm.article_id = a.article_id order by cm.create_time desc")
    List<Comment> listAll();

    @Insert("insert into `comment` (article_id, user_id, content, create_time) values (#{articleId}, #{userId}, #{content}, now())")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "comment_id")
    int insert(Comment comment);

    @Delete("delete from `comment` where comment_id = #{id}")
    int deleteById(@Param("id") Long id);

    @Select("select count(*) from `comment` where user_id = #{userId}")
    long countByUser(@Param("userId") Long userId);
}

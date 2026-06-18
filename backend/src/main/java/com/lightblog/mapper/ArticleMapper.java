package com.lightblog.mapper;

import com.lightblog.entity.Article;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface ArticleMapper {
    String ARTICLE_COLUMNS = "a.article_id as id, a.title, a.content, a.category_id as categoryId, c.category_name as categoryName, a.tags, a.user_id as userId, u.nickname as authorName, a.is_public as publicFlag, a.comment_enable as commentEnable, a.create_time as createTime, a.update_time as updateTime";

    @Select("<script>"
            + "select " + ARTICLE_COLUMNS + " from article a "
            + "join `user` u on a.user_id = u.user_id "
            + "left join category c on a.category_id = c.category_id "
            + "where a.is_public = 1 "
            + "<if test='keyword != null and keyword != \"\"'>and (a.title like concat('%', #{keyword}, '%') or a.tags like concat('%', #{keyword}, '%') or u.nickname like concat('%', #{keyword}, '%')) </if>"
            + "<if test='categoryId != null'>and a.category_id = #{categoryId} </if>"
            + "order by a.create_time desc limit #{size} offset #{offset}"
            + "</script>")
    List<Article> listPublic(@Param("keyword") String keyword, @Param("categoryId") Long categoryId, @Param("offset") int offset, @Param("size") int size);

    @Select("<script>"
            + "select count(*) from article a join `user` u on a.user_id = u.user_id where a.is_public = 1 "
            + "<if test='keyword != null and keyword != \"\"'>and (a.title like concat('%', #{keyword}, '%') or a.tags like concat('%', #{keyword}, '%') or u.nickname like concat('%', #{keyword}, '%')) </if>"
            + "<if test='categoryId != null'>and a.category_id = #{categoryId} </if>"
            + "</script>")
    long countPublic(@Param("keyword") String keyword, @Param("categoryId") Long categoryId);

    @Select("select " + ARTICLE_COLUMNS + " from article a join `user` u on a.user_id = u.user_id left join category c on a.category_id = c.category_id where a.article_id = #{id}")
    Article selectById(@Param("id") Long id);

    @Select("select " + ARTICLE_COLUMNS + " from article a join `user` u on a.user_id = u.user_id left join category c on a.category_id = c.category_id where a.user_id = #{userId} order by a.create_time desc")
    List<Article> listByUser(@Param("userId") Long userId);

    @Select("<script>"
            + "select " + ARTICLE_COLUMNS + " from article a "
            + "join `user` u on a.user_id = u.user_id "
            + "left join category c on a.category_id = c.category_id "
            + "where 1 = 1 "
            + "<if test='title != null and title != \"\"'>and a.title like concat('%', #{title}, '%') </if>"
            + "<if test='author != null and author != \"\"'>and (u.nickname like concat('%', #{author}, '%') or u.username like concat('%', #{author}, '%')) </if>"
            + "order by a.create_time desc"
            + "</script>")
    List<Article> adminList(@Param("title") String title, @Param("author") String author);

    @Insert("insert into article (title, content, category_id, tags, user_id, is_public, comment_enable, create_time, update_time) values (#{title}, #{content}, #{categoryId}, #{tags}, #{userId}, #{publicFlag}, #{commentEnable}, now(), now())")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "article_id")
    int insert(Article article);

    @Update("update article set title = #{title}, content = #{content}, category_id = #{categoryId}, tags = #{tags}, is_public = #{publicFlag}, comment_enable = #{commentEnable}, update_time = now() where article_id = #{id}")
    int update(Article article);

    @Delete("delete from article where article_id = #{id}")
    int deleteById(@Param("id") Long id);

    @Select("select count(*) from article where user_id = #{userId}")
    long countByUser(@Param("userId") Long userId);
}

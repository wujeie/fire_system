package com.wzs.fire.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wzs.fire.pojo.entity.Firearticles;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface FirearticlesMapper extends BaseMapper<Firearticles>{
    int deleteByPrimaryKey(Integer articleId);

    int updateByPrimaryKeySelective(Firearticles record);

    int updateByPrimaryKey(Firearticles record);
    @Insert("insert into firearticles (title, content, created_at, updated_at) values (#{title}, #{content}, #{createdAt}, #{updatedAt})")
    int addArticle(Firearticles record);

    @Select("select article_id, title,content from firearticles where article_id = #{articleId}")
    Firearticles selectDetailById(Integer articleId);
}
package com.wzs.fire.mapper;

import com.wzs.fire.pojo.entity.Questions;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuestionsMapper {
    int deleteByPrimaryKey(Integer questionId);

    int insert(Questions record);

    int insertSelective(Questions record);

    Questions selectByPrimaryKey(Integer questionId);

    int updateByPrimaryKeySelective(Questions record);

    int updateByPrimaryKey(Questions record);

    @Select("select * from questions")
    List<Questions> selectAll();
}
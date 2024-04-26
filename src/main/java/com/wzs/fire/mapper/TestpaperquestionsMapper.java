package com.wzs.fire.mapper;

import com.alibaba.druid.sql.ast.statement.SQLInsertInto;
import com.wzs.fire.pojo.entity.Testpaperquestions;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TestpaperquestionsMapper {
    int deleteByPrimaryKey(Integer testpaperquestionsId);

    int insert(Testpaperquestions record);

    int insertSelective(Testpaperquestions record);

    Testpaperquestions selectByPrimaryKey(Integer testpaperquestionsId);

    int updateByPrimaryKeySelective(Testpaperquestions record);

    int updateByPrimaryKey(Testpaperquestions record);



    @Select("SELECT MAX(question_order) AS max_order FROM testpaperquestions WHERE testpaper_id = #{testpaperId}")
    Integer selectOrderByTestpaperId(Integer testpaperId);

    @Select("select testpaper_id from testpaperquestions where question_id = #{questionId}")
    List<Integer> getPaperIdByQuestion(Integer questionId);
    @Delete("delete from testpaperquestions where question_id = #{param1} and testpaper_id = #{param2}")
    void deleteByQuestionIdAndTestpaperId(Integer questionId, Integer paperId);
    @Delete("delete from testpaperquestions where question_id = #{questionId}")
    void deleteByQuestionId(Integer questionId);

    @Select("select question_id from testpaperquestions where testpaper_id = #{testpaperId}")
    Integer[] getQuestionsIdBytestpaperId(Integer testpaperId);

    @Select("select question_id from testpaperquestions where testpaper_id = #{testpaperId} order by question_order")
    Integer[] getQuestionsIdBytestpaperIdByOrder(Integer testpaperId);
}
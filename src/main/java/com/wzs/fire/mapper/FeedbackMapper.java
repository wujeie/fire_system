package com.wzs.fire.mapper;

import com.wzs.fire.pojo.entity.Feedback;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FeedbackMapper {
    int deleteByPrimaryKey(Integer feedbackId);

    int insert(Feedback record);

    int insertSelective(Feedback record);

    Feedback selectByPrimaryKey(Integer feedbackId);

    int updateByPrimaryKeySelective(Feedback record);

    int updateByPrimaryKey(Feedback record);

    @Select("select count(*) from feedback where admin_reply is null or admin_reply = '';")
    Integer getUnResponsesCount();



    @Select("select * from feedback")
    List<Feedback> selectAll();

    @Select("select count(*) from feedback")
    Integer selectCount();

    @Select("select * from feedback where user_id = #{userId}")
    List<Feedback> selectByUserId(Integer userId);
}
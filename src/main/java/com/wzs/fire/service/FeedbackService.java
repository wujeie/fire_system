package com.wzs.fire.service;

import com.wzs.fire.common.Result;
import com.wzs.fire.util.DateUtil;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.wzs.fire.mapper.FeedbackMapper;
import com.wzs.fire.pojo.entity.Feedback;

import java.util.List;

@Service
public class FeedbackService{

    @Resource
    private FeedbackMapper feedbackMapper;

    
    public int deleteByPrimaryKey(Integer feedbackId) {
        return feedbackMapper.deleteByPrimaryKey(feedbackId);
    }

    
    public int insert(Feedback record) {
        return feedbackMapper.insert(record);
    }

    
    public int insertSelective(Feedback record) {
        return feedbackMapper.insertSelective(record);
    }

    
    public Feedback selectByPrimaryKey(Integer feedbackId) {
        return feedbackMapper.selectByPrimaryKey(feedbackId);
    }

    
    public int updateByPrimaryKeySelective(Feedback record) {
        return feedbackMapper.updateByPrimaryKeySelective(record);
    }

    
    public int updateByPrimaryKey(Feedback record) {
        return feedbackMapper.updateByPrimaryKey(record);
    }

    public Result<Integer> getUnResponsesCount() {
        Integer count;
        count = feedbackMapper.getUnResponsesCount();
        return Result.success(200,"success",count);
    }

    public Result<Integer> getHasResponseSCount() {
        Integer count;
        count = feedbackMapper.selectCount() - feedbackMapper.getUnResponsesCount();
        return Result.success(200,"success",count);
    }

    public Result<List<Feedback>> selectAll() {
        List<Feedback> feedbacks = feedbackMapper.selectAll();
        return Result.success(200,"success",feedbacks);
    }

    public Result<Feedback> getSelectById(Integer feedbackId) {
        Feedback feedback = feedbackMapper.selectByPrimaryKey(feedbackId);
        return Result.success(200,"success",feedback);
    }

    public Result<Integer> reply(Feedback feedback) {
        feedback.setRepliedAt(DateUtil.getSqlDate());
        return Result.success(200,"success",feedbackMapper.updateByPrimaryKeySelective(feedback));
    }

    public Result<List<Feedback>> selectByUserId(Integer userId) {
        return Result.success(200,"success",feedbackMapper.selectByUserId(userId));
    }


}

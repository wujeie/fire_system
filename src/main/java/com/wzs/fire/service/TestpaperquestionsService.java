package com.wzs.fire.service;

import com.wzs.fire.common.Result;
import com.wzs.fire.pojo.entity.Testpapers;
import org.bytedeco.opencv.opencv_dnn.LSTMLayer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import com.wzs.fire.mapper.TestpaperquestionsMapper;
import com.wzs.fire.pojo.entity.Testpaperquestions;

import java.util.ArrayList;
import java.util.List;

@Service
public class TestpaperquestionsService {

    @Resource
    private TestpaperquestionsMapper testpaperquestionsMapper;
    @Resource
    private TestpapersService testpapersService;

    public int deleteByPrimaryKey(Integer testpaperquestionsId) {
        return testpaperquestionsMapper.deleteByPrimaryKey(testpaperquestionsId);
    }


    public int insert(Testpaperquestions record) {
        return testpaperquestionsMapper.insert(record);
    }


    public int insertSelective(Testpaperquestions record) {
        return testpaperquestionsMapper.insertSelective(record);
    }


    public Testpaperquestions selectByPrimaryKey(Integer testpaperquestionsId) {
        return testpaperquestionsMapper.selectByPrimaryKey(testpaperquestionsId);
    }


    public int updateByPrimaryKeySelective(Testpaperquestions record) {
        return testpaperquestionsMapper.updateByPrimaryKeySelective(record);
    }


    public int updateByPrimaryKey(Testpaperquestions record) {
        return testpaperquestionsMapper.updateByPrimaryKey(record);
    }

    public Result assignQuestion(List<Integer> testpaperIds, Integer questionId) {
        //得到这个问题所在的所有试卷
        List<Integer> PaperIds = testpaperquestionsMapper.getPaperIdByQuestion(questionId);
        //删除
        //对比得到的List和传入的List，如果传入的List中没有这个试卷，就删除
        for (Integer paperId : PaperIds) {
            if (!testpaperIds.contains(paperId)) {
                testpaperquestionsMapper.deleteByQuestionIdAndTestpaperId(questionId, paperId);
            }
        }
        //如果有这个试卷，就不删除
        //得到传入的List中有，但是数据库中没有的试卷
        for (Integer testpaperId : testpaperIds) {
            if (!PaperIds.contains(testpaperId)) {
                //get order
                Integer order = testpaperquestionsMapper.selectOrderByTestpaperId(testpaperId);
                if (order == null) {
                    order = 0;
                }
                order += 1;
                //封装
                Testpaperquestions testpaperquestions = new Testpaperquestions();
                testpaperquestions.setQuestionId(questionId);
                testpaperquestions.setTestpaperId(testpaperId);
                testpaperquestions.setQuestionOrder(order);
                //要添加的
                //insert
                testpaperquestionsMapper.insert(testpaperquestions);
            }
        }
        return Result.success();
    }

    public Result<List<Integer>> getPaperIdsByQuestion(Integer questionId) {
        List<Integer> paperIdByQuestion = testpaperquestionsMapper.getPaperIdByQuestion(questionId);
        return Result.success(200,"success",paperIdByQuestion);
    }

    public Result<List<Testpapers>> getPapersByQuestion(Integer questionId) {
        List<Integer> paperIdByQuestion = testpaperquestionsMapper.getPaperIdByQuestion(questionId);
        List<Testpapers> testpapers = new ArrayList<>();
        for (Integer integer : paperIdByQuestion) {
            testpapers.add(testpapersService.selectByPrimaryKey(integer));
        }
        return Result.success(200,"success",testpapers);
    }

    public Result<List<Testpapers>> getAllPapers() {
        return testpapersService.getAllPapers();
    }
}

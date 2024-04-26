package com.wzs.fire.service;

import com.wzs.fire.common.Result;
import com.wzs.fire.mapper.TestpaperquestionsMapper;
import com.wzs.fire.pojo.dto.QuestionDto;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.wzs.fire.pojo.entity.Questions;
import com.wzs.fire.mapper.QuestionsMapper;

import java.util.List;
import java.util.Map;

@Service
public class QuestionsService{

    @Resource
    private QuestionsMapper questionsMapper;

    @Resource
    TestpaperquestionsMapper testpaperquestionsMapper;
    public int deleteByPrimaryKey(Integer questionId) {
        return questionsMapper.deleteByPrimaryKey(questionId);
    }

    
    public int insert(Questions record) {
        return questionsMapper.insert(record);
    }

    
    public int insertSelective(Questions record) {
        return questionsMapper.insertSelective(record);
    }

    
    public Questions selectByPrimaryKey(Integer questionId) {
        return questionsMapper.selectByPrimaryKey(questionId);
    }

    
    public int updateByPrimaryKeySelective(Questions record) {
        return questionsMapper.updateByPrimaryKeySelective(record);
    }

    
    public int updateByPrimaryKey(Questions record) {
        return questionsMapper.updateByPrimaryKey(record);
    }


    public Result<List<Questions>> selectQuestionList() {
        return Result.success(200,"success",questionsMapper.selectAll());
    }

    public Result deleteQuestion(Integer questionId) {
        questionsMapper.deleteByPrimaryKey(questionId);
        testpaperquestionsMapper.deleteByQuestionId(questionId);
        return Result.success();
    }

    public Result addQuestion(QuestionDto questionDto) {
        Questions questions = new Questions();
        questions.setContent(questionDto.getContent());
        Map<String, String> options = questionDto.getOptions();
        questions.setOptionA(options.get("A"));
        questions.setOptionB(options.get("B"));
        questions.setOptionC(options.get("C"));
        questions.setOptionD(options.get("D"));
        questions.setCorrectAnswer(questionDto.getCorrectAnswer());
        questionsMapper.insert(questions);
        return Result.success();
    }
}

package com.wzs.fire.service;

import com.wzs.fire.common.Result;
import com.wzs.fire.mapper.QuestionsMapper;
import com.wzs.fire.mapper.TestpaperquestionsMapper;
import com.wzs.fire.pojo.dto.TestPaperQuestionsDto;
import com.wzs.fire.pojo.entity.Questions;
import com.wzs.fire.pojo.vo.TestPaperQuestionsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import com.wzs.fire.mapper.TestpapersMapper;
import com.wzs.fire.pojo.entity.Testpapers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TestpapersService {

    @Resource
    private TestpapersMapper testpapersMapper;

    @Autowired
    private TestpaperquestionsMapper testpaperquestionsMapper;

    @Autowired
    private QuestionsMapper questionsMapper;

    @Autowired
    private QuestionsService questionsService;

    public int deleteByPrimaryKey(Integer testpaperId) {
        return testpapersMapper.deleteByPrimaryKey(testpaperId);
    }


    public int insert(Testpapers record) {
        return testpapersMapper.insert(record);
    }


    public int insertSelective(Testpapers record) {
        return testpapersMapper.insertSelective(record);
    }


    public Testpapers selectByPrimaryKey(Integer testpaperId) {
        return testpapersMapper.selectByPrimaryKey(testpaperId);
    }


    public int updateByPrimaryKeySelective(Testpapers record) {
        return testpapersMapper.updateByPrimaryKeySelective(record);
    }


    public int updateByPrimaryKey(Testpapers record) {
        return testpapersMapper.updateByPrimaryKey(record);
    }

    public Result<List<Testpapers>> getAllPapers() {
        List<Testpapers> testpapers = testpapersMapper.getAllpapers();
        return Result.success(200, "success", testpapers);
    }

    public Result<List<Testpapers>> getAllTestPaper() {
        return Result.success(200, "success", testpapersMapper.getAllpapers());
    }

    public Result getTestPaperQuestions(Integer testpaperId) {
        TestPaperQuestionsVo testPaperQuestionsVo = new TestPaperQuestionsVo();
        Testpapers testpapers = testpapersMapper.selectByPrimaryKey(testpaperId);
        testPaperQuestionsVo.setTestpaperName(testpapers.getTestpaperName());
        testPaperQuestionsVo.setTestpaperId(testpaperId);
        testPaperQuestionsVo.setTotalscore(testpapers.getTotalscore());
        Integer[] questionIds = testpaperquestionsMapper.getQuestionsIdBytestpaperId(testpaperId);
        for (Integer questionId : questionIds) {
            Questions questions = questionsService.selectByPrimaryKey(questionId);
            testPaperQuestionsVo.getQuestionList().add(questions);
        }
        return Result.success(200, "success", testPaperQuestionsVo);
    }

    public Result submitAnswers(TestPaperQuestionsDto testPaperQuestionsDto) {
        //得到正确答案
        Integer[] questionsIdBytestpaperId = testpaperquestionsMapper.getQuestionsIdBytestpaperIdByOrder(testPaperQuestionsDto.getTestpaperId());
        List<String> correctAnswers = new ArrayList<>();
        for (Integer questionId : questionsIdBytestpaperId) {
            Questions questions = questionsService.selectByPrimaryKey(questionId);
            correctAnswers.add(questions.getCorrectAnswer());
        }
        //得到用户错误的题目题号
        List<Integer> wrongQuestions = new ArrayList<>();
        //得到用户的答案
        String[] answers = testPaperQuestionsDto.getAnswers();
        //比较答案,得到错误的题目题号,用户对了n题
        Integer n = 0;
        for (int i = 0; i < answers.length; i++) {
            if (!answers[i].equals(correctAnswers.get(i))) {
                wrongQuestions.add(i);
            }else {
                n++;
            }
        }
        //得到用户的分数
            //得到试卷总分
        Testpapers testpapers = testpapersMapper.selectByPrimaryKey(testPaperQuestionsDto.getTestpaperId());
        int totalScore = Integer.parseInt(testpapers.getTotalscore());
            //计算用户的分数
        double userScoreDouble = totalScore * (n / (double)answers.length); // 注意：这里将 answers.length 转换为 double 以执行浮点除法
        // 使用 String.format() 保留两位小数并转换为字符串
        String userScoreString = String.format("%.2f", userScoreDouble);
        String message;
        if (wrongQuestions.size() == 0) {
            message = "恭喜你，全部答对了！"+"你的分数为："+userScoreString;
        } else {
            List<Integer> collect = new ArrayList<>();
            for (Integer wrongQuestion : wrongQuestions) {
                collect.add(wrongQuestion + 1);
            }
            String wrongQuestionString = collect.stream()
                    .map(Object::toString) // 将每个Integer转换为String
                    .collect(Collectors.joining(","));
            message = "你答错了" + wrongQuestions.size() + "道题，题号为：" + wrongQuestionString + "你的分数为："+userScoreString;
        }
        return Result.success(200,message,userScoreDouble);
    }
}

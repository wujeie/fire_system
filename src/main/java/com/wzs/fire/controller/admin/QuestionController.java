package com.wzs.fire.controller.admin;

import com.wzs.fire.common.Result;
import com.wzs.fire.mapper.TestpaperquestionsMapper;
import com.wzs.fire.pojo.dto.QuestionDto;
import com.wzs.fire.pojo.dto.TestPaperQuestionDto;
import com.wzs.fire.pojo.entity.Questions;
import com.wzs.fire.pojo.entity.Testpapers;
import com.wzs.fire.service.QuestionsService;
import com.wzs.fire.service.TestpaperquestionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    QuestionsService questionsService;
    @Autowired
    TestpaperquestionsService testpaperquestionsService;
    @GetMapping("/getQuestionList")
    public Result<List<Questions>> getQuestionList(){
        return questionsService.selectQuestionList();
    }
    @PutMapping("/assignQuestion")
    public  Result assignQuestion(@RequestBody TestPaperQuestionDto testPaperQuestionDto){
        List<Integer> testpaperIds = testPaperQuestionDto.getTestpaperIds();
        Integer questionId = testPaperQuestionDto.getQuestionId();
        return testpaperquestionsService.assignQuestion(testpaperIds,questionId);
    }
    @GetMapping("/getPaperByQuestion")
    public Result<List<Testpapers>> getPaperIdsByQuestion(Integer questionId){
        return testpaperquestionsService.getPapersByQuestion(questionId);
    }
    @GetMapping("/getAllPapers")
    public Result<List<Testpapers>> getAllPapers(){
        return testpaperquestionsService.getAllPapers();
    }
    @GetMapping("/getPaperIdsByQuestionId")
    public Result<List<Integer>> getPaperIdsByQuestionId(Integer questionId){
        return testpaperquestionsService.getPaperIdsByQuestion(questionId);
    }
    @DeleteMapping("/deleteQuestion")
    public Result deleteQuestion(Integer questionId){
        return questionsService.deleteQuestion(questionId);
    }
    @PostMapping("/addQuestion")
    public Result addQuestion(@RequestBody QuestionDto questionDto){
        return questionsService.addQuestion(questionDto);
    }
}

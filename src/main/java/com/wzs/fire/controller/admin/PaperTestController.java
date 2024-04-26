package com.wzs.fire.controller.admin;

import com.wzs.fire.common.Result;
import com.wzs.fire.pojo.dto.TestPaperQuestionsDto;
import com.wzs.fire.pojo.entity.Testpapers;
import com.wzs.fire.pojo.vo.TestPaperQuestionsVo;
import com.wzs.fire.service.TestpapersService;
import com.wzs.fire.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
    @RequestMapping("/testPaper")
public class PaperTestController {
    @Autowired
    TestpapersService testpapersService;
    @GetMapping("/getAllTestPaper")
    public Result<List<Testpapers>> getAllTestPaper(){
        return testpapersService.getAllTestPaper();
    }
    @PostMapping("/addTestPaper")
    public Result addTestPaper(@RequestBody Testpapers testpapers){
        testpapers.setCreatetime(DateUtil.getSqlDate());
        int insert = testpapersService.insert(testpapers);
        if(insert==0){
            return Result.error("添加失败");
        }
        return Result.success();
    }
    @DeleteMapping("/deleteTestPaper")
    public Result deleteTestPaper(@RequestParam Integer testpaperId){
        int i = testpapersService.deleteByPrimaryKey(testpaperId);
        if(i==0){
            return Result.error("删除失败");
        }
        return Result.success();
    }
    @GetMapping("/getOneTestPaper")
    public Result<Testpapers> getOneTestPaper(@RequestParam Integer testpaperId){
        Testpapers testpapers = testpapersService.selectByPrimaryKey(testpaperId);
        return Result.success(200,"success",testpapers);
    }
    @PutMapping("/updateTestPaper")
    public Result updateTestPaper(@RequestBody Testpapers testpapers){
        System.out.println("testpapers:"+testpapers);
        int i = testpapersService.updateByPrimaryKeySelective(testpapers);
        if(i==0){
            return Result.error("更新失败");
        }
        return Result.success();
    }
    @GetMapping("/getTestPaperQuestions")
    public Result getTestPaperQuestions(@RequestParam Integer testpaperId){
        return testpapersService.getTestPaperQuestions(testpaperId);
    }
    @PostMapping("/submitAnswers")
    public Result submitAnswers(@RequestBody TestPaperQuestionsDto testPaperQuestionsDto){
        return testpapersService.submitAnswers(testPaperQuestionsDto);
    }
}

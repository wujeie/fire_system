package com.wzs.fire.controller.admin;

import com.wzs.fire.common.Result;
import com.wzs.fire.pojo.entity.Feedback;
import com.wzs.fire.service.FeedbackService;
import com.wzs.fire.util.DateUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * (fkp_system.feedback)表控制层
 *
 * @author 吴征世
 */
@RestController
@RequestMapping("/message")
public class FeedbackController {
    /**
     * 服务对象
     */
    @Resource
    private FeedbackService feedbackService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/selectOne")
    public Feedback selectOne(Integer id) {
        return feedbackService.selectByPrimaryKey(id);
    }

    @GetMapping("/selectAll")
    public Result<List<Feedback>> selectAll() {
        return feedbackService.selectAll();
    }

    @GetMapping("/getSelectById")
    public Result<Feedback> getSelectById(Integer feedbackId) {
        return feedbackService.getSelectById(feedbackId);
    }
    @PutMapping("/reply")
    public Result<Integer> reply(@RequestBody Feedback feedback) {
        return feedbackService.reply(feedback);
    }
    @DeleteMapping("/delete")
    public Result<Integer> delete(Integer feedbackId) {
        return Result.success(200,"success",feedbackService.deleteByPrimaryKey(feedbackId));
    }
    @GetMapping("/selectByUserId")
    public Result<List<Feedback>> selectByUserId(Integer userId) {
        return feedbackService.selectByUserId(userId);
    }
    @PostMapping("/addMessage")
    public Result addMessage(@RequestBody Feedback feedback) {
        feedback.setCreatedAt(DateUtil.getSqlDate());
        feedbackService.insert(feedback);
        return Result.success();
    }
    @GetMapping("/getUnResponsesCount")
    public Result<Integer> getUnResponsesCount() {
        return feedbackService.getUnResponsesCount();
    }
    @GetMapping("/getHasResponsesCount")
    public Result<Integer> getHasResponsesCount() {
        return feedbackService.getHasResponseSCount();
    }
}
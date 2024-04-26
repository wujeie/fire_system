package com.wzs.fire.controller.admin;

import com.wzs.fire.common.Result;
import com.wzs.fire.pojo.entity.Firearticles;
import com.wzs.fire.service.FirearticlesService;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private FirearticlesService firearticlesService;
    @GetMapping("/getArticleList")
    public Result<List<Firearticles>> getArticleList(){
        return firearticlesService.getArticleList();
    }

    @GetMapping("/getArticleDetail")
    public Result<Firearticles> getArticleDetail(Integer articleId){
        return firearticlesService.getArticleDetail(articleId);
    }

    @PostMapping("/updateArticle")
    public Result updateArticle(@RequestBody Firearticles firearticles){
        return firearticlesService.updateArticle(firearticles);
    }
    @PutMapping("/addArticle")
    public Result addArticle(@RequestBody Firearticles firearticles){
        System.out.println(firearticles);
        return firearticlesService.addArticle(firearticles);
    }
    @DeleteMapping("/deleteArticle")
    public Result deleteArticle(Integer articleId){
        firearticlesService.deleteByPrimaryKey(articleId);
        return Result.success();
    }
}

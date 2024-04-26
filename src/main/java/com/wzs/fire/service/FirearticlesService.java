package com.wzs.fire.service;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.wzs.fire.common.Result;
import com.wzs.fire.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.wzs.fire.pojo.entity.Firearticles;
import com.wzs.fire.mapper.FirearticlesMapper;

import java.sql.Date;
import java.util.List;

import static org.apache.coyote.http11.Constants.a;

@Service
public class FirearticlesService{

    @Resource
    private FirearticlesMapper firearticlesMapper;

    
    public int deleteByPrimaryKey(Integer articleId) {
        return firearticlesMapper.deleteByPrimaryKey(articleId);
    }

    
    public int insert(Firearticles record) {
        return firearticlesMapper.insert(record);
    }

    
    public int updateByPrimaryKeySelective(Firearticles record) {
        return firearticlesMapper.updateByPrimaryKeySelective(record);
    }

    
    public int updateByPrimaryKey(Firearticles record) {
        return firearticlesMapper.updateByPrimaryKey(record);
    }

    public Result<List<Firearticles>> getArticleList() {
        List<Firearticles> articlesList = firearticlesMapper.selectList(null);
        return Result.success(200,"success",articlesList);
    }

    public Result<Firearticles> getArticleDetail(Integer articleId) {
        Firearticles firearticles = firearticlesMapper.selectDetailById(articleId);
        return Result.success(200,"success",firearticles);
    }

    public Result updateArticle(Firearticles firearticles) {
        UpdateWrapper<Firearticles> firearticlesUpdateWrapper = new UpdateWrapper<Firearticles>();
        firearticlesUpdateWrapper.eq("article_id",firearticles.getArticleId());
        firearticles.setUpdatedAt(DateUtil.getSqlDate());
        firearticlesMapper.update(firearticles,firearticlesUpdateWrapper);
        return Result.success();
    }

    public Result addArticle(Firearticles firearticles) {
        Firearticles firearticle = new Firearticles();
        firearticle.setTitle(firearticles.getTitle());
        firearticle.setContent(firearticles.getContent());
        firearticle.setCreatedAt(DateUtil.getSqlDate());
        firearticle.setUpdatedAt(DateUtil.getSqlDate());
        firearticlesMapper.addArticle(firearticle);
        return Result.success();
    }
}

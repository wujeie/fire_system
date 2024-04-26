package com.wzs.fire.service;

import com.wzs.fire.common.Result;
import com.wzs.fire.controller.admin.VideoController;
import com.wzs.fire.util.DateUtil;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.wzs.fire.pojo.entity.Firevideos;
import com.wzs.fire.mapper.FirevideosMapper;

import java.io.File;
import java.util.List;

@Service
public class FirevideosService{

    @Resource
    private FirevideosMapper firevideosMapper;

    
    public int deleteByPrimaryKey(Integer videoId) {
        return firevideosMapper.deleteByPrimaryKey(videoId);
    }

    
    public int insert(Firevideos record) {
        return firevideosMapper.insert(record);
    }

    
    public int insertSelective(Firevideos record) {
        return firevideosMapper.insertSelective(record);
    }

    
    public Firevideos selectByPrimaryKey(Integer videoId) {
        return firevideosMapper.selectByPrimaryKey(videoId);
    }

    
    public int updateByPrimaryKeySelective(Firevideos record) {
        return firevideosMapper.updateByPrimaryKeySelective(record);
    }

    
    public int updateByPrimaryKey(Firevideos record) {
        return firevideosMapper.updateByPrimaryKey(record);
    }

    public Result<List<Firevideos>> getVideoList() {
         List<Firevideos> firevideos = firevideosMapper.selectAll();
        return Result.success(200,"success",firevideos);
    }

    public void upload(String title, String videoUrl, String coverUrl) {
        Firevideos firevideos = new Firevideos();
        firevideos.setTitle(title);
        firevideos.setFilePath(videoUrl);
        firevideos.setVideoCover(coverUrl);
        firevideos.setCreatedAt(DateUtil.getSqlDate());
        firevideosMapper.insert(firevideos);
    }

    public void deleteVideo(Integer videoId) {
        Firevideos firevideos = firevideosMapper.selectByPrimaryKey(videoId);
        String videoUrl = "D:/FireStatic"+firevideos.getFilePath();
        String coverUrl = "D:/FireStatic"+firevideos.getVideoCover();
        File videoFile = new File(videoUrl);
        File coverFile = new File(coverUrl);
        if(videoFile.exists()){
            videoFile.delete();
        }
        if(coverFile.exists()){
            coverFile.delete();
        }
        deleteByPrimaryKey(videoId);
    }
}

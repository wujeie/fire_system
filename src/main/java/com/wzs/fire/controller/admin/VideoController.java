package com.wzs.fire.controller.admin;

import com.wzs.fire.common.Result;
import com.wzs.fire.pojo.entity.Firevideos;
import com.wzs.fire.service.FirevideosService;
import com.wzs.fire.util.VideoCoverUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.tags.ParamTag;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/video")
@CrossOrigin
public class VideoController {
    public static final String VIDEO_FILE_PATH = "D:/FireStatic/video";
    public static final String VIDEO_COVER_FILE_PATH = "D:/FireStatic/videoCover";
    @Autowired
    private FirevideosService firevideosService;
    @GetMapping("/getVideoList")
    public Result<List<Firevideos>> getVideoList(){
        return firevideosService.getVideoList();
    }
    @PostMapping("/upload")
    public Result upload(@RequestParam("file") MultipartFile file,@RequestParam("title")String title){
        if (file.isEmpty()){
            return Result.error("上传失败，请选择文件");
        }try {
            String fileName = file.getOriginalFilename();
            int lastIndex = fileName.lastIndexOf('.');
            if(!fileName.substring(lastIndex+1).equals("mp4")){
                return Result.error("请上传mp4格式的视频");
            }
            Path filePath = Paths.get(VIDEO_FILE_PATH).resolve(fileName);
            file.transferTo(filePath);
            String videoUrl = "/video/"+fileName;
            String fileNameWithoutExtension = (lastIndex == -1) ? fileName : fileName.substring(0, lastIndex);
            //服务器封面地址
            String coverUrl = "/videoCover/"+fileNameWithoutExtension+".jpg";
            //封面本地地址
            String coverPath = VIDEO_COVER_FILE_PATH+"/"+fileNameWithoutExtension+".jpg";
            //保存封面
            VideoCoverUtil.extractFirstFrameAsThumbnail(filePath.toString(), coverPath);
            firevideosService.upload(title,videoUrl,coverUrl);
            return Result.success();
        }catch (IOException e){
            e.printStackTrace();
            return Result.error("文件上传失败");
        }
    }
    @GetMapping("/getVideoById")
    public Result<Firevideos> getVideoById(Integer videoId){
        Firevideos firevideos = firevideosService.selectByPrimaryKey(videoId);
        return Result.success(firevideos);
    }
    @DeleteMapping ("/deleteVideo")
    public Result<Firevideos> deleteVideo(Integer videoId){
        firevideosService.deleteVideo(videoId);
        return Result.success();
    }
}

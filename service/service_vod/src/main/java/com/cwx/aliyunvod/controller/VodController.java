package com.cwx.aliyunvod.controller;

import com.cwx.aliyunvod.service.Vodervice;
import com.cwx.commonutils.R;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/***
 @author  *
 @date 2023/10/29$ 16:25$*
 @description: */
@RestController
@RequestMapping("/eduvod/video")
@CrossOrigin
public class VodController {

    @Autowired
    Vodervice vodervice;

    //上传视频到阿里云
    @PostMapping("/uploadAliVideo")
    @ApiOperation("上传视频到阿里云")
    public R uploadAliyiVideo(MultipartFile file) throws IOException {
        String videoId = vodervice.uploadAliyiVideo(file);
        return R.ok().data("VideoId", videoId);
    }

    @DeleteMapping("/removeAlyVideo/{id}")
    @ApiOperation("删除视频")
    public R removeAlyVideo(@PathVariable String id) {
        vodervice.removeAliVideo(id);
        return R.ok();
    }

    @DeleteMapping("/delete-batch")
    @ApiOperation("批量删除视频")
    public R removeAlyVideos(@RequestParam("videoIdList") List<String> videoIdList) {
        vodervice.removeAliVideos(videoIdList);
        return R.ok();
    }
}

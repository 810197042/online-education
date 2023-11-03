package com.cwx.serviceedu.controller;


import com.cwx.commonutils.R;
import com.cwx.serviceedu.entity.EduVideo;
import com.cwx.serviceedu.service.EduVideoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author cwx
 * @since 2023-10-26
 */
@RestController
@RequestMapping("/eduservice/video")
@CrossOrigin
public class EduVideoController {

    @Autowired
    private EduVideoService eduVideoService;

    @PostMapping("/addVideo")
    @ApiOperation("添加小节")
    public R addVideo(@RequestBody EduVideo eduVideo) {
        eduVideoService.save(eduVideo);
        return R.ok();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除小节同时删除视频")
    //需要完善，同时删除视频源
    public R deleteVideo(@PathVariable String id) {
        eduVideoService.removeVideoAndAliVod(id);
        return R.ok();
    }

    @PostMapping("/updateVideo")
    @ApiOperation("修改小节")
    public R updateVideo(@RequestBody EduVideo eduVideo) {
        eduVideoService.updateById(eduVideo);
        return R.ok();
    }


}


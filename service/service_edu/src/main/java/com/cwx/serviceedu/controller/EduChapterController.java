package com.cwx.serviceedu.controller;


import com.cwx.commonutils.R;
import com.cwx.serviceedu.entity.EduChapter;
import com.cwx.serviceedu.entity.chapter.ChapterVo;
import com.cwx.serviceedu.service.EduChapterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author cwx
 * @since 2023-10-26
 */
@RestController
@RequestMapping("/eduservice/chapter")
@Api(description = "章节管理")
//@CrossOrigin
public class EduChapterController {

    @Autowired
    EduChapterService eduChapterService;

    @GetMapping("/getChapterVideoList/{courseId}")
    @ApiOperation(value = "根据课程id查询章节和小节")
    public R getChapterVideo(@PathVariable(value = "courseId") String courseId) {
        List<ChapterVo> res = eduChapterService.getChapterVideo(courseId);
        return R.ok().data("allChapterVideo", res);
    }

    @PostMapping("/addChapter")
    @ApiOperation(value = "添加章节,测试的时候不要加id和时间，这是自动生成的")
    public R addChapter(@RequestBody EduChapter eduChapter) {
        eduChapterService.save(eduChapter);
        return R.ok();
    }

    @GetMapping("/getChapterInfo/{chapterId}")
    @ApiOperation(value = "获得章节信息")
    public R getChapterInfo(@PathVariable String chapterId) {
        EduChapter chapter = eduChapterService.getById(chapterId);
        return R.ok().data("chapter", chapter);
    }

    @PostMapping("/updateChapter")
    @ApiOperation(value = "修改章节信息，测试的时候不要加id和时间，这是自动生成的")
    public R updateChapterInfo(@RequestBody EduChapter eduChapter) {
        eduChapterService.updateById(eduChapter);
        return R.ok();
    }

    @DeleteMapping("/{chapterId}")
    @ApiOperation(value = "根据id删除章节信息")
    public R deleteChapterInfo(@PathVariable String chapterId) {
        eduChapterService.removeChapterAndVideo(chapterId);
        return R.ok();
    }
}


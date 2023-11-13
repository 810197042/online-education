package com.cwx.serviceedu.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cwx.commonutils.R;
import com.cwx.serviceedu.entity.EduCourse;
import com.cwx.serviceedu.entity.vo.CourseInfoVo;
import com.cwx.serviceedu.entity.vo.CoursePublishVo;
import com.cwx.serviceedu.service.EduCourseService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
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
@RequestMapping("/eduservice/course")
//@CrossOrigin
public class EduCourseController {

    @Autowired
    EduCourseService eduCourseService;


    //条件查询+分页
    @PostMapping("/pageCourseCondition/{cur}/{size}")
    @ApiOperation(value = "条件查询+分页")
    public R getCoursePage(@RequestBody(required = false) EduCourse eduCourse, @PathVariable("cur") int cur, @PathVariable("size") int size) {
        Page<EduCourse> page = new Page<>(cur, size);
        LambdaQueryWrapper<EduCourse> queryWrapper = new LambdaQueryWrapper<>();
        if (eduCourse != null) {
            queryWrapper.like(!StringUtils.isEmpty(eduCourse.getTitle()), EduCourse::getTitle, eduCourse.getTitle());
            queryWrapper.eq(!StringUtils.isEmpty(eduCourse.getStatus()), EduCourse::getStatus, eduCourse.getStatus());
        }
        queryWrapper.orderByDesc(EduCourse::getGmtModified);
        eduCourseService.page(page, queryWrapper);
        return R.ok().data("total", page.getTotal()).data("rows", page.getRecords());
    }

    @PostMapping("/addCourseInfo")
    @ApiOperation(value = "传入courseVo对象，同时插入course和description表")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        eduCourseService.addCourseInfo(courseInfoVo);
        return R.ok();
    }

    @GetMapping("/getCourseInfo/{courseId}")
    @ApiOperation(value = "查询courseVo对象")
    public R getCourseInfo(@PathVariable(value = "courseId") String courseId) {
        CourseInfoVo courseInfoVo = eduCourseService.getCourseInfoVo(courseId);
        return R.ok().data("courseInfoVo", courseInfoVo);
    }

    @PostMapping("/updateCourseInfo")
    @ApiOperation(value = "传入courseVo对象，同时更新course和description表")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        eduCourseService.updateCourseInfo(courseInfoVo);
        return R.ok();
    }

    @GetMapping("/getPublishCourseInfo/{courseId}")
    @ApiOperation(value = "查询课程发布信息")
    public R getCoursePublishVo(@PathVariable String courseId) {
        CoursePublishVo coursePushlishInfo = eduCourseService.getCoursePushlishInfo(courseId);
        return R.ok().data("publishCourse", coursePushlishInfo);
    }

    @PostMapping("/publishCourse/{id}")
    @ApiOperation(value = "发布课程")
    public R publishCourse(@PathVariable String id) {
        EduCourse course = new EduCourse();
        course.setId(id);
        course.setStatus("Normal");
        eduCourseService.updateById(course);
        return R.ok();
    }

    @DeleteMapping("/{courseId}")
    @ApiOperation(value = "删除课程，同时删除简介、视频、章节、小节")
    public R deleteCourse(@PathVariable String courseId) {
        eduCourseService.deleteCourse(courseId);
        return R.ok();
    }
}


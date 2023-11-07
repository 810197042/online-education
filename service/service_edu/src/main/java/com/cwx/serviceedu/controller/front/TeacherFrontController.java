package com.cwx.serviceedu.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cwx.commonutils.R;
import com.cwx.serviceedu.entity.EduCourse;
import com.cwx.serviceedu.entity.EduTeacher;
import com.cwx.serviceedu.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.JsonPath;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/***
 @author  *
 @date 2023/11/2$ 15:25$*
 @description: */
@RestController
@RequestMapping("/eduservice/teacherfront")
@CrossOrigin
@Api("讲师（前端）")
public class TeacherFrontController {

    @Autowired
    private EduTeacherService eduTeacherService;

    @ApiOperation("分页查询讲师（前端）")
    @PostMapping("/getTeacherFrontList/{page}/{limit}")
    public R getTeacherFrontList(@PathVariable("page") Long page, @PathVariable("limit") Long limit) {
        Page<EduTeacher> teacherPage = new Page<>(page, limit);
        Map<String, Object> map = eduTeacherService.getTeacherFrontList(page, limit);
        return R.ok().data(map);
    }

    @GetMapping("/getTeacherFrontInfo/{teacherId}")
    @ApiOperation("查询讲师以及该讲师所讲的课程")
    public R getTeacherFrontInfo(@PathVariable String teacherId) {
        EduTeacher teacher = eduTeacherService.getById(teacherId);
        List<EduCourse> courses= eduTeacherService.getCourseByTeacherId(teacherId);
        return R.ok().data("teacher", teacher).data("courseList", courses);
    }

}

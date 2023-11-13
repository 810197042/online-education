package com.cwx.serviceedu.controller.front;

import com.cwx.commonutils.R;
import com.cwx.serviceedu.entity.EduCourse;
import com.cwx.serviceedu.entity.EduTeacher;
import com.cwx.serviceedu.service.EduCourseService;
import com.cwx.serviceedu.service.EduTeacherService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/***
 @author  *
 @date 2023/10/31$ 16:31$*
 @description: */
@RestController
@RequestMapping("/eduservice/indexfront")
//@CrossOrigin
public class IndexFrontController {

    @Autowired
    private EduCourseService eduCourseService;

    @Autowired
    private EduTeacherService eduTeacherService;

    @GetMapping("/index")
    @ApiOperation("id降序查出前8个热门课程和前4个名师")
    public R index1() {
        List<EduCourse> courseList = eduCourseService.seletTop8Courses();
        List<EduTeacher> teacherList = eduTeacherService.seletTop4Teachers();
        return R.ok().data("eduList", courseList).data("teacherList", teacherList);
    }

}

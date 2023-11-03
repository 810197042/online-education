package com.cwx.serviceedu.controller.front;

import com.cwx.commonutils.R;
import com.cwx.serviceedu.entity.frontVo.CourseFrontVo;
import com.cwx.serviceedu.service.EduCourseService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/***
 @author  *
 @date 2023/11/2$ 16:26$*
 @description: */
@RestController
@RequestMapping("/eduservice/coursefront")
@CrossOrigin
public class CourseFrontController {

    @Autowired
    private EduCourseService eduCourseService;

    @PostMapping("/getFrontCourseList/{page}/{limit}")
    @ApiOperation("条件查询带分页(前端)")
    public R getFrontCourseList(@PathVariable("page") long page, @PathVariable("limit") long limit, @RequestBody(required = false) CourseFrontVo courseFrontVo) {
        Map<String, Object> map = eduCourseService.getFrontCourseList(page, limit, courseFrontVo);
        return R.ok().data(map);
    }
}

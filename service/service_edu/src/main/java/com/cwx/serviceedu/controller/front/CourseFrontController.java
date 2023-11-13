package com.cwx.serviceedu.controller.front;

import com.cwx.commonutils.JwtUtils;
import com.cwx.commonutils.R;
import com.cwx.commonutils.feignclient.OrderClient;
import com.cwx.commonutils.user.CourseWebVoOrder;
import com.cwx.serviceedu.entity.chapter.ChapterVo;
import com.cwx.serviceedu.entity.frontVo.CourseFrontVo;
import com.cwx.serviceedu.entity.frontVo.CourseWebVo;
import com.cwx.serviceedu.service.EduChapterService;
import com.cwx.serviceedu.service.EduCourseService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/***
 @author  *
 @date 2023/11/2$ 16:26$*
 @description: */
@RestController
@RequestMapping("/eduservice/coursefront")
//@CrossOrigin
public class CourseFrontController {

    @Autowired
    private EduCourseService eduCourseService;

    @Autowired
    private EduChapterService eduChapterService;

    @Autowired
    private OrderClient orderClient;

    @PostMapping("/getFrontInfo/{page}/{limit}")
    @ApiOperation("条件查询带分页(前端)")
    public R getFrontCourseList(@PathVariable("page") long page, @PathVariable("limit") long limit, @RequestBody(required = false) CourseFrontVo courseFrontVo) {
        Map<String, Object> map = eduCourseService.getFrontCourseList(page, limit, courseFrontVo);
        return R.ok().data(map);
    }

    @GetMapping("/getFrontCourseInfo/{courseId}")
    @ApiOperation("根据课程id查信息")
    public R getFrontCourseInfo(@PathVariable String courseId, HttpServletRequest request) {
        //根据id写sql查课程信息
        CourseWebVo courseWebVo = eduCourseService.getCourseWebVo(courseId);
        //根据id查章节和小节
        List<ChapterVo> chapterVideo = eduChapterService.getChapterVideo(courseId);

        //根据课程id和用户id查询课程是否已支付
        boolean buyCourse = orderClient.isBuyCourse(courseId, JwtUtils.getMemberIdByJwtToken(request));
        return R.ok().data("courseWebVo",courseWebVo).data("chapterVideoList", chapterVideo).data("isBuy", buyCourse);
    }

    @PostMapping("/getCourseInfoOrder/{id}")
    public CourseWebVoOrder getCourseInfoOrder(@PathVariable String id) {
        CourseWebVo courseWebVo = eduCourseService.getCourseWebVo(id);
        CourseWebVoOrder courseWebVoOrder = new CourseWebVoOrder();
        BeanUtils.copyProperties(courseWebVo, courseWebVoOrder);
        return courseWebVoOrder;
    }
}

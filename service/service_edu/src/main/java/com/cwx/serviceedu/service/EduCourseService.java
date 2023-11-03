package com.cwx.serviceedu.service;

import com.cwx.serviceedu.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cwx.serviceedu.entity.frontVo.CourseFrontVo;
import com.cwx.serviceedu.entity.vo.CourseInfoVo;
import com.cwx.serviceedu.entity.vo.CoursePublishVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author cwx
 * @since 2023-10-26
 */
public interface EduCourseService extends IService<EduCourse> {

    void addCourseInfo(CourseInfoVo courseInfoVo);

    CourseInfoVo getCourseInfoVo(String courseId);

    void updateCourseInfo(CourseInfoVo courseInfoVo);

    CoursePublishVo getCoursePushlishInfo(String courseId);

    void deleteCourse(String courseId);

    List<EduCourse> seletTop8Courses();

    Map<String, Object> getFrontCourseList(long page, long limit, CourseFrontVo courseFrontVo);
}

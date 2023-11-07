package com.cwx.serviceedu.mapper;

import com.cwx.serviceedu.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cwx.serviceedu.entity.frontVo.CourseWebVo;
import com.cwx.serviceedu.entity.vo.CoursePublishVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author cwx
 * @since 2023-10-26
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    CoursePublishVo getCoursePushlishInfo(@Param(value = "id") String courseId);

    CourseWebVo getCourseWebVo(@Param(value = "courseId") String courseId);
}

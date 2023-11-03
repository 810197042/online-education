package com.cwx.serviceedu.service;

import com.cwx.serviceedu.entity.EduCourse;
import com.cwx.serviceedu.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author cwx
 * @since 2023-10-18
 */
public interface EduTeacherService extends IService<EduTeacher> {

    List<EduTeacher> seletTop4Teachers();

    Map<String, Object> getTeacherFrontList(Long page, Long limit);

    List<EduCourse> getCourseByTeacherId(String teacherId);
}

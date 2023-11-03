package com.cwx.serviceedu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cwx.serviceedu.entity.EduCourse;
import com.cwx.serviceedu.entity.EduTeacher;
import com.cwx.serviceedu.mapper.EduTeacherMapper;
import com.cwx.serviceedu.service.EduCourseService;
import com.cwx.serviceedu.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author cwx
 * @since 2023-10-18
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    @Autowired
    private EduCourseService eduCourseService;

    @Cacheable(key = "'selectTop4'", value = "Teacher")
    @Override
    public List<EduTeacher> seletTop4Teachers() {
        LambdaQueryWrapper<EduTeacher> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(EduTeacher::getId);
        queryWrapper.last("limit 4");
        List<EduTeacher> list = baseMapper.selectList(queryWrapper);
        return list;
    }

    @Override
    public Map<String, Object> getTeacherFrontList(Long page, Long limit) {
        Page<EduTeacher> pageTeacher = new Page<>(page, limit);
        QueryWrapper<EduTeacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        //分页封装到pageTeacher中
        baseMapper.selectPage(pageTeacher, queryWrapper);
        List<EduTeacher> records = pageTeacher.getRecords();
        long current = pageTeacher.getCurrent();
        long pages = pageTeacher.getPages();
        long size = pageTeacher.getSize();
        long total = pageTeacher.getTotal();

        boolean hasNext = pageTeacher.hasNext();//上一页
        boolean hasPrevious = pageTeacher.hasPrevious();//下一页

        Map<String, Object> map = new HashMap<>();

        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return  map;
    }

    @Override
    public List<EduCourse> getCourseByTeacherId(String teacherId) {
        LambdaQueryWrapper<EduCourse> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(EduCourse::getTeacherId, teacherId);
        List<EduCourse> courses = eduCourseService.list(queryWrapper);
        return courses;
    }
}

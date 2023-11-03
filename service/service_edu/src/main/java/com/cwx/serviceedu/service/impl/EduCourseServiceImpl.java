package com.cwx.serviceedu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cwx.servicebase.exceptionhandler.MyException;
import com.cwx.serviceedu.entity.EduChapter;
import com.cwx.serviceedu.entity.EduCourse;
import com.cwx.serviceedu.entity.EduCourseDescription;
import com.cwx.serviceedu.entity.EduVideo;
import com.cwx.serviceedu.entity.frontVo.CourseFrontVo;
import com.cwx.serviceedu.entity.vo.CourseInfoVo;
import com.cwx.serviceedu.entity.vo.CoursePublishVo;
import com.cwx.serviceedu.mapper.EduCourseDescriptionMapper;
import com.cwx.serviceedu.mapper.EduCourseMapper;
import com.cwx.serviceedu.service.EduChapterService;
import com.cwx.serviceedu.service.EduCourseDescriptionService;
import com.cwx.serviceedu.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cwx.serviceedu.service.EduVideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author cwx
 * @since 2023-10-26
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    EduCourseDescriptionMapper eduCourseDescriptionMapper;

    @Autowired
    EduCourseDescriptionService eduCourseDescriptionService;

    @Autowired
    EduChapterService eduChapterService;

    @Autowired
    EduVideoService eduVideoService;

    @Override
    public void addCourseInfo(CourseInfoVo courseInfoVo) {
        EduCourse eduCourse = new EduCourse();
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        BeanUtils.copyProperties(courseInfoVo, eduCourseDescription);
        int insert1 = baseMapper.insert(eduCourse);
        if (insert1 <= 0) {
            throw new MyException(20001, "course表插入失败");
        }
        eduCourseDescription.setId(eduCourse.getId());
        boolean save = eduCourseDescriptionService.save(eduCourseDescription);
        if (!save) {
            throw new MyException(20001, "description表插入失败");
        }
    }

    @Override
    public CourseInfoVo getCourseInfoVo(String courseId) {
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        EduCourse eduCourse = baseMapper.selectById(courseId);
        EduCourseDescription eduCourseDescription = eduCourseDescriptionMapper.selectById(courseId);
        BeanUtils.copyProperties(eduCourse, courseInfoVo);
        BeanUtils.copyProperties(eduCourseDescription, courseInfoVo);
        return courseInfoVo;
    }

    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        EduCourse eduCourse = new EduCourse();
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        BeanUtils.copyProperties(courseInfoVo, eduCourseDescription);
        int i = baseMapper.updateById(eduCourse);
        if (i <= 0) {
            throw new MyException(20001, "course更新失败");
        }
        i = eduCourseDescriptionMapper.updateById(eduCourseDescription);
        if (i <= 0) {
            throw new MyException(20001, "description更新失败");
        }
    }

    @Override
    public CoursePublishVo getCoursePushlishInfo(String courseId) {
        CoursePublishVo coursePushlishInfo = baseMapper.getCoursePushlishInfo(courseId);
        return coursePushlishInfo;
    }

    @Override
    public void deleteCourse(String courseId) {
        baseMapper.deleteById(courseId);

        eduCourseDescriptionService.removeById(courseId);

        eduVideoService.removeByCourseId(courseId);

        eduChapterService.removeByCourseId(courseId);

    }

    @Cacheable(key = "'selectTop8'", value = "Course")
    @Override
    public List<EduCourse> seletTop8Courses() {
        LambdaQueryWrapper<EduCourse> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(EduCourse::getId);
        queryWrapper.last("limit 8");
        List<EduCourse> list = baseMapper.selectList(queryWrapper);
        return list;
    }

    @Override
    public Map<String, Object> getFrontCourseList(long page, long limit, CourseFrontVo courseFrontVo) {
        LambdaQueryWrapper<EduCourse> queryWrapper = new LambdaQueryWrapper<>();
        Page<EduCourse> queryVoPage = new Page<>(page, limit);
        //判断条件是否为空
        if (!StringUtils.isEmpty(courseFrontVo.getSubjectParentId())){//一级分类
            queryWrapper.eq(EduCourse::getSubjectParentId, courseFrontVo.getSubjectParentId());
        }
        if (!StringUtils.isEmpty(courseFrontVo.getSubjectId())){//二级分类
            queryWrapper.eq(EduCourse::getSubjectId, courseFrontVo.getSubjectId());
        }
        if (!StringUtils.isEmpty(courseFrontVo.getBuyCountSort())) {//销量排序
            queryWrapper.orderByDesc(EduCourse::getBuyCount);
        }
        if (!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort())) {//时间排序
            queryWrapper.orderByDesc(EduCourse::getGmtCreate);
        }
        if (!StringUtils.isEmpty(courseFrontVo.getPriceSort())) {//价格排序
            queryWrapper.orderByDesc(EduCourse::getPrice);
        }

        //封装到page里面
        baseMapper.selectPage(queryVoPage, queryWrapper);

        long total = queryVoPage.getTotal();
        List<EduCourse> records = queryVoPage.getRecords();
        long current = queryVoPage.getCurrent();
        long size = queryVoPage.getSize();
        boolean hasNext = queryVoPage.hasNext();
        boolean hasPrevious = queryVoPage.hasPrevious();
        long pages = queryVoPage.getPages();

        HashMap<String, Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;
    }
}

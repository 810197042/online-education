package com.cwx.serviceedu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cwx.servicebase.exceptionhandler.MyException;
import com.cwx.serviceedu.entity.excel.EasyExcelSubject;
import com.cwx.serviceedu.entity.EduSubject;
import com.cwx.serviceedu.entity.listener.SubjectListener;
import com.cwx.serviceedu.entity.subject.OneSubject;
import com.cwx.serviceedu.entity.subject.TwoSubject;
import com.cwx.serviceedu.mapper.EduSubjectMapper;
import com.cwx.serviceedu.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author cwx
 * @since 2023-10-25
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Autowired
    EduSubjectService eduSubjectService;

    @Override
    public void addSubject(MultipartFile file) {
        try {
            InputStream in = file.getInputStream();
            EasyExcel.read(in, EasyExcelSubject.class, new SubjectListener(eduSubjectService)).sheet().doRead();
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new MyException(20001, "写入excel失败");
        }
    }

    @Override
    public List<OneSubject> getAllSubject() {
        List<OneSubject> res = new ArrayList<>();
        LambdaQueryWrapper<EduSubject> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(EduSubject::getParentId, "0");
        List<EduSubject> oneSubjects = baseMapper.selectList(queryWrapper);
        queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.ne(EduSubject::getParentId, "0");
        List<EduSubject> twoSubjects = baseMapper.selectList(queryWrapper);
        for (EduSubject oneSubject : oneSubjects) {
            OneSubject one = new OneSubject();
            //one.setTitle(oneSubject.getTitle());
            //one.setId(oneSubject.getId());
            BeanUtils.copyProperties(oneSubject, one);
            List<TwoSubject> children = new ArrayList<>();
            for (EduSubject twoSubject: twoSubjects) {
                if (twoSubject.getParentId().equals(oneSubject.getId())) {
                    TwoSubject two = new TwoSubject();
                    BeanUtils.copyProperties(twoSubject, two);
                    children.add(two);
                }
            }
            one.setChildren(children);
            res.add(one);
        }
        return res;
    }


}

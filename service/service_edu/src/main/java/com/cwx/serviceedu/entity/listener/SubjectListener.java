package com.cwx.serviceedu.entity.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cwx.serviceedu.entity.excel.EasyExcelSubject;
import com.cwx.serviceedu.entity.EduSubject;
import com.cwx.serviceedu.service.EduSubjectService;

/***
 @author  *
 @date 2023/10/25$ 16:37$*
 @description: */
public class SubjectListener extends AnalysisEventListener<EasyExcelSubject> {

    EduSubjectService eduSubjectService;

    public SubjectListener(EduSubjectService eduSubjectService) {
        this.eduSubjectService = eduSubjectService;
    }

    private EduSubject getOneSubject(String title) {
        LambdaQueryWrapper<EduSubject> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(EduSubject::getTitle, title);
        queryWrapper.eq(EduSubject::getParentId, "0");
        EduSubject one = eduSubjectService.getOne(queryWrapper);
        return one;
    }

    private EduSubject getTwoSubject(EduSubject one, String subject) {
        String pid = one.getId();
        LambdaQueryWrapper<EduSubject> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(EduSubject::getTitle, subject);
        queryWrapper.eq(EduSubject::getParentId, pid);
        EduSubject two = eduSubjectService.getOne(queryWrapper);
        return two;
    }

    @Override
    public void invoke(EasyExcelSubject easyExcelSubject, AnalysisContext analysisContext) {
        String title = easyExcelSubject.getOneSubjectName();
        String subject = easyExcelSubject.getTwoSubjectName();
        EduSubject oneSubject = getOneSubject(title);
        if (oneSubject == null) {
            oneSubject = new EduSubject();
            oneSubject.setParentId("0");
            oneSubject.setTitle(title);
            eduSubjectService.save(oneSubject);
        }
        EduSubject twoSubject = getTwoSubject(oneSubject, subject);
        if (twoSubject == null) {
            twoSubject = new EduSubject();
            twoSubject.setParentId(oneSubject.getId());
            twoSubject.setTitle(subject);
            eduSubjectService.save(twoSubject);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}

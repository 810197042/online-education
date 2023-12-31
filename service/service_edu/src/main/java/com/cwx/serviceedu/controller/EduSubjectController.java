package com.cwx.serviceedu.controller;


import com.cwx.commonutils.R;
import com.cwx.serviceedu.entity.subject.OneSubject;
import com.cwx.serviceedu.service.EduSubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author cwx
 * @since 2023-10-25
 */
@Api(description = "课程管理")
@RestController
@RequestMapping("/eduservice/subject")
//@CrossOrigin
public class EduSubjectController {

    @Autowired
    EduSubjectService eduSubjectService;

    //前端会传来一个excel文件，后端读取后保存到数据库edu_subject表中
    @PostMapping("/addSubject")
    @ApiOperation(value = "excel文件传给后端并把内容写入数据库")
    public R addSubject(MultipartFile file) {
        eduSubjectService.addSubject(file);
        return R.ok();
    }

    @GetMapping("/getAllSubject")
    @ApiOperation(value = "课程分类显示")
    public R getAllSubject() {
        List<OneSubject> res = eduSubjectService.getAllSubject();
        return R.ok().data("list", res);
    }
}


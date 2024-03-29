package com.cwx.serviceedu.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cwx.commonutils.R;
import com.cwx.servicebase.exceptionhandler.MyException;
import com.cwx.serviceedu.entity.EduTeacher;
import com.cwx.serviceedu.entity.vo.TeachQuery;
import com.cwx.serviceedu.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author cwx
 * @since 2023-10-18
 */
@Api(description = "讲师管理")
@RestController
//@CrossOrigin
@RequestMapping("/eduservice/teacher")
public class EduTeacherController {

    @Autowired
    private EduTeacherService teacherService;

    @GetMapping()
    public R findAllTeacher() {
        List<EduTeacher> list = teacherService.list();
        R r = R.ok().data("items", list);
        return r;
    }

    @ApiOperation(value = "逻辑删除讲师")
    @DeleteMapping("{id}")
    public R removeTeacher(@ApiParam(name = "id", value = "讲师id", required = true) @PathVariable String id) {
        boolean remove = teacherService.removeById(id);
        return remove ? R.ok() : R.error();
    }

    //分页查询
    @PostMapping("/pageList/{current}/{limit}")
    @ApiOperation(value = "分页查询讲师")
    public R pageListTeacher(@PathVariable Long current, @PathVariable Long limit) {
        Page<EduTeacher>pageTeacher = new Page<>(current, limit);
        teacherService.page(pageTeacher);//把数据封装到page对象里
        long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();
        //return R.ok().data("results", pageTeacher);
        return R.ok().data("total", total).data("items", records);
    }

    @ApiOperation(value = "条件带分页查询讲师")
    @PostMapping("/moreConditionPageList/{page}/{limit}")
    public R pageTeacherCondition(@PathVariable("page") int page, @PathVariable("limit") int limit,
    @RequestBody(required = false) TeachQuery teachQuery) {
        Page<EduTeacher> pageTeacher = new Page<>(page, limit);

        LambdaQueryWrapper<EduTeacher> queryWrapper = new LambdaQueryWrapper<>();
        if (teachQuery != null) {
            String name = teachQuery.getName();
            Integer level = teachQuery.getLevel();
            String begin = teachQuery.getBegin();
            String end = teachQuery.getEnd();
            queryWrapper.like(!StringUtils.isEmpty(name), EduTeacher::getName, name);
            queryWrapper.eq(!StringUtils.isEmpty(level), EduTeacher::getLevel, level);
            queryWrapper.ge(!StringUtils.isEmpty(begin), EduTeacher::getGmtCreate, begin);
            queryWrapper.le(!StringUtils.isEmpty(end), EduTeacher::getGmtModified, end);
        }
        teacherService.page(pageTeacher, queryWrapper);
        long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();
        return R.ok().data("total", total).data("items", records);
    }

    @ApiOperation(value = "添加讲师")
    @PostMapping("/saveTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher) {
        if (teacherService.save(eduTeacher)) {
            return R.ok();
        }
        return R.error();
    }

    @ApiOperation(value = "根据ID查讲师")
    @GetMapping("/getTeacherInfo/{id}")
    public R getTeacher(@PathVariable String id) {
        EduTeacher eduTeacher = teacherService.getById(id);
        return R.ok().data("eduTeacher", eduTeacher);
    }

    @ApiOperation(value = "修改讲师")
    @PostMapping("/updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean flag = teacherService.updateById(eduTeacher);
        return flag ? R.ok() : R.error();
    }
}


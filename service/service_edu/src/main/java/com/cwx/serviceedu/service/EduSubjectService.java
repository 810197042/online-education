package com.cwx.serviceedu.service;

import com.cwx.serviceedu.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cwx.serviceedu.entity.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author cwx
 * @since 2023-10-25
 */
public interface EduSubjectService extends IService<EduSubject> {

    void addSubject(MultipartFile file);

    List<OneSubject> getAllSubject();

    void removeRootAndChildrenById(String id);
}

package com.cwx.serviceedu.service;

import com.cwx.serviceedu.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cwx.serviceedu.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author cwx
 * @since 2023-10-26
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> getChapterVideo(String courseId);

    void removeChapterAndVideo(String chapterId);

    void removeByCourseId(String courseId);
}

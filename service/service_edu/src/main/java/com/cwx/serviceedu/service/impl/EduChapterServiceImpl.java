package com.cwx.serviceedu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cwx.servicebase.exceptionhandler.MyException;
import com.cwx.serviceedu.entity.EduChapter;
import com.cwx.serviceedu.entity.EduVideo;
import com.cwx.serviceedu.entity.chapter.ChapterVo;
import com.cwx.serviceedu.entity.chapter.VideoVo;
import com.cwx.serviceedu.mapper.EduChapterMapper;
import com.cwx.serviceedu.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cwx.serviceedu.service.EduVideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author cwx
 * @since 2023-10-26
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    EduVideoService eduVideoService;

    @Override
    public List<ChapterVo> getChapterVideo(String courseId) {
        List<ChapterVo> res = new ArrayList<>();
        LambdaQueryWrapper<EduChapter> eduChapterLambdaQueryWrapper = new LambdaQueryWrapper<>();
        eduChapterLambdaQueryWrapper.eq(EduChapter::getCourseId, courseId);
        List<EduChapter> eduChapters = baseMapper.selectList(eduChapterLambdaQueryWrapper);
        LambdaQueryWrapper<EduVideo> eduVideoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        eduVideoLambdaQueryWrapper.eq(EduVideo::getCourseId, courseId);
        List<EduVideo> eduVideos = eduVideoService.list(eduVideoLambdaQueryWrapper);
        for (EduChapter eduChapter : eduChapters) {
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter, chapterVo);
            for (EduVideo eduVideo : eduVideos) {
                if (eduVideo.getChapterId().equals(eduChapter.getId())) {
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo, videoVo);
                    chapterVo.getChildren().add(videoVo);
                }
            }
            res.add(chapterVo);
        }
        return res;
    }

    @Override
    public void removeChapterAndVideo(String chapterId) {
        LambdaQueryWrapper<EduVideo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(EduVideo::getChapterId, chapterId);
        int count = eduVideoService.count(queryWrapper);
        if (count > 0) {
            throw new MyException(20001, "本章节中包含小节，不能删除本章节");
        }
        int i = baseMapper.deleteById(chapterId);
        if (i <= 0) {
            throw new MyException(20001, "删除章节失败");
        }
        /*boolean remove = eduVideoService.remove(queryWrapper);
        if (!remove) {
            throw new MyException(20001, "删除小节失败");
        }*/
    }

    @Override
    public void removeByCourseId(String courseId) {
        LambdaQueryWrapper<EduChapter> eduChapterLambdaQueryWrapper = new LambdaQueryWrapper<>();
        eduChapterLambdaQueryWrapper.eq(EduChapter::getCourseId, courseId);
        baseMapper.delete(eduChapterLambdaQueryWrapper);
    }
}

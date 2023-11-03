package com.cwx.serviceedu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.cwx.commonutils.R;
import com.cwx.commonutils.feignclient.VodClient;
import com.cwx.servicebase.exceptionhandler.MyException;
import com.cwx.serviceedu.entity.EduChapter;
import com.cwx.serviceedu.entity.EduVideo;
import com.cwx.serviceedu.mapper.EduVideoMapper;
import com.cwx.serviceedu.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author cwx
 * @since 2023-10-26
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Autowired
    VodClient vodClient;


    @Override
    public void removeByCourseId(String courseId) {
        LambdaQueryWrapper<EduVideo> eduVideoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        eduVideoLambdaQueryWrapper.eq(EduVideo::getCourseId, courseId);
        List<EduVideo> eduVideos = baseMapper.selectList(eduVideoLambdaQueryWrapper);
        List<String> ids = eduVideos.stream().map(eduVideo -> eduVideo.getVideoSourceId()).filter(id -> !StringUtils.isEmpty(id)).collect(Collectors.toList());
        //删除视频
        if (ids.size() > 0) {
            vodClient.removeAlyVideos(ids);
        }
        baseMapper.delete(eduVideoLambdaQueryWrapper);
    }

    @Override
    public void removeVideoAndAliVod(String id){
        EduVideo eduVideo = baseMapper.selectById(id);
        if (!StringUtils.isEmpty(eduVideo.getVideoSourceId())) {
            R r = vodClient.removeAlyVideo(eduVideo.getVideoSourceId());
            if (r.getCode() == 20001) {
                throw new MyException(20001, "删除视频出错...");
            }

        }
        baseMapper.deleteById(id);

    }
}

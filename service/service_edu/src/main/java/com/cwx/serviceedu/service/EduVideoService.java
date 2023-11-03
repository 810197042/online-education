package com.cwx.serviceedu.service;

import com.cwx.serviceedu.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author cwx
 * @since 2023-10-26
 */
public interface EduVideoService extends IService<EduVideo> {

    void removeByCourseId(String courseId);

    void removeVideoAndAliVod(String id);
}

package com.cwx.commonutils.feignclient.impl;

import com.cwx.commonutils.R;
import com.cwx.commonutils.feignclient.VodClient;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/***
 @author  *
 @date 2023/10/30$ 21:01$*
 @description: */
//熔断器，出错之后会执行对应的方法
@Component
public class VodFileDegradeFeignClient implements VodClient {
    @Override
    public R uploadAliyiVideo(MultipartFile file) {
        return R.error().message("上传视频出错");
    }

    @Override
    public R removeAlyVideo(String id){
        return R.error().message("删除视频出错");
    }

    @Override
    public R removeAlyVideos(List<String> videoIdList) {
        return R.error().message("批量删除视频出错");
    }
}

package com.cwx.aliyunvod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;

import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.cwx.aliyunvod.Utils.ConstantProperties;
import com.cwx.aliyunvod.service.Vodervice;
import com.cwx.servicebase.exceptionhandler.MyException;
import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;


/***
 @author  *
 @date 2023/10/29$ 16:26$*
 @description: */
@Service
public class VodServiceImpl implements Vodervice {

    @Autowired
    DefaultAcsClient client;

    @Override
    public String uploadAliyiVideo(MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();
            String fileName = file.getOriginalFilename();
            String title = fileName.substring(0, fileName.lastIndexOf("."));
            UploadStreamRequest request = new UploadStreamRequest(ConstantProperties.ACCESS_KEY_ID, ConstantProperties.ACCESS_KEY_SECRET, title, fileName, inputStream);
            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);
            System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
            if (response.isSuccess()) {
                System.out.print("VideoId=" + response.getVideoId() + "\n");
                return response.getRequestId();
            } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                System.out.print("VideoId=" + response.getVideoId() + "\n");
                System.out.print("ErrorCode=" + response.getCode() + "\n");
                System.out.print("ErrorMessage=" + response.getMessage() + "\n");
            }
            throw new MyException(20001, "视频/音频上传失败");
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new MyException(20001, "视频/音频上传失败");
        }
    }

    @Override
    public void removeAliVideo(String id) {

        DeleteVideoRequest request = new DeleteVideoRequest();
        request.setVideoIds(id);
        try {
            DeleteVideoResponse response = client.getAcsResponse(request);
            System.out.println(new Gson().toJson(response));
        } catch (ServerException e) {
            e.printStackTrace();
            throw new MyException(20001, "删除视频失败");
        }
        catch (ClientException e) {
            System.out.println("ErrCode:" + e.getErrCode());
            System.out.println("ErrMsg:" + e.getErrMsg());
            System.out.println("RequestId:" + e.getRequestId());
            throw new MyException(20001, "删除视频失败");
        }

    }

    @Override
    public void removeAliVideos(List<String> videoIdList) {
        DeleteVideoRequest request = new DeleteVideoRequest();
        String ids = StringUtils.join(videoIdList.toArray(), ",");
        request.setVideoIds(ids);
        try {
            DeleteVideoResponse response = client.getAcsResponse(request);
            System.out.println(new Gson().toJson(response));
        } catch (ServerException e) {
            e.printStackTrace();
            throw new MyException(20001, "批量删除视频失败");
        }
        catch (ClientException e) {
            System.out.println("ErrCode:" + e.getErrCode());
            System.out.println("ErrMsg:" + e.getErrMsg());
            System.out.println("RequestId:" + e.getRequestId());
            throw new MyException(20001, "批量删除视频失败");
        }
    }
}

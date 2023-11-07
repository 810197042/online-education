package com.cwx.aliyunvod.service;

import com.aliyuncs.exceptions.ClientException;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface Vodervice {
    String uploadAliyiVideo(MultipartFile file) throws IOException;

    void removeAliVideo(String id);

    void removeAliVideos(List<String> videoIdList);

    String getPlayAuth(String id) throws ClientException;
}

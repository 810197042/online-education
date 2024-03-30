package com.cwx.oss.controller;


import com.cwx.commonutils.R;
import com.cwx.oss.service.OssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/eduoss/fileoss")
@Api(description = "OSS模块")
//@CrossOrigin
public class OssController {
    @Autowired
    private OssService ossService;

    @PostMapping
    @ApiOperation(value = "上传文件到OSS")
    public R uploadOssFile(MultipartFile file) {
        //获取上传文件，MultipartFile
        //返回上传oss的url
        String url = ossService.upload(file);
        return R.ok().data("url", url);
    }
}

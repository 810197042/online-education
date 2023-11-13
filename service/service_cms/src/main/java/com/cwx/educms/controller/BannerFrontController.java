package com.cwx.educms.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cwx.commonutils.R;
import com.cwx.educms.entity.CrmBanner;
import com.cwx.educms.service.CrmBannerService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/***
 @author  *
 @date 2023/10/31$ 15:58$*
 @description: */
@RestController
@RequestMapping("/educms/bannerfront")
//@CrossOrigin
public class BannerFrontController {
    @Autowired
    private CrmBannerService crmBannerService;

    //查询所有banner
    @GetMapping("/getAllBanner")
    @ApiOperation("根据id降序排序，选出前2条")
    public R getAllBanner(){
        //根据id排序，选出前两条
        List<CrmBanner> List = crmBannerService.listTop2();
        return R.ok().data("list",List);
    }
}

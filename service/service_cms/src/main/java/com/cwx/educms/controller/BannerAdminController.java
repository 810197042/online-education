package com.cwx.educms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cwx.commonutils.R;
import com.cwx.educms.entity.CrmBanner;
import com.cwx.educms.service.CrmBannerService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author cwx
 * @since 2023-10-31
 */
@RestController
@RequestMapping("/educms/banneradmin")
@CrossOrigin
public class BannerAdminController {

    @Autowired
    CrmBannerService crmBannerService;

    @GetMapping("/pageBanner/{cur}/{limit}")
    @ApiOperation("分页显示")
    public R pageBanner(@PathVariable("cur") Integer cur, @PathVariable("limit") Integer limit) {
        Page<CrmBanner> page = new Page<>(cur, limit);
        crmBannerService.page(page);
        return R.ok().data("items", page.getRecords()).data("total", page.getTotal());
    }

    //添加banner
    @PostMapping("/addBanner")
    public R addBanner(@RequestBody CrmBanner crmBanner){
        crmBannerService.save(crmBanner);
        return R.ok();
    }

    //根据id获取Banner
    @ApiOperation(value = "获取Banner")
    @GetMapping("/get/{id}")
    public R get(@PathVariable String id) {
        CrmBanner banner = crmBannerService.getById(id);
        return R.ok().data("item", banner);
    }

    //修改Banner
    @ApiOperation(value = "修改Banner")
    @PutMapping("/update")
    public R updateById(@RequestBody CrmBanner banner) {
        crmBannerService.updateById(banner);
        return R.ok();
    }

    //根据id删除Banner
    @ApiOperation(value = "删除Banner")
    @DeleteMapping("/remove/{id}")
    public R remove(@PathVariable String id) {
        crmBannerService.removeById(id);
        return R.ok();
    }

}


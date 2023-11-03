package com.cwx.educms.service;

import com.cwx.educms.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author cwx
 * @since 2023-10-31
 */
public interface CrmBannerService extends IService<CrmBanner> {

    List<CrmBanner> listTop2();
}

package com.cwx.educms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cwx.educms.entity.CrmBanner;
import com.cwx.educms.mapper.CrmBannerMapper;
import com.cwx.educms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author cwx
 * @since 2023-10-31
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {


    @Cacheable(key = "'selectIndexList'", value = "banner")
    @Override
    public List<CrmBanner> listTop2() {
        LambdaQueryWrapper<CrmBanner> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(CrmBanner::getId);
        queryWrapper.last("limit 2");
        List<CrmBanner> list = baseMapper.selectList(queryWrapper);
        return list;
    }
}

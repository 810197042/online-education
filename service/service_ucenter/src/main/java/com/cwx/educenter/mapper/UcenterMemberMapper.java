package com.cwx.educenter.mapper;

import com.cwx.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 */


public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {

    Integer ucenterMemberService(String day);
}

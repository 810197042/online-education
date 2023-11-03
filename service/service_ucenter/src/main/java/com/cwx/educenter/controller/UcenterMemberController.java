package com.cwx.educenter.controller;



import com.cwx.commonutils.JwtUtils;
import com.cwx.commonutils.R;
import com.cwx.commonutils.user.UcenterMemberOrder;
import com.cwx.educenter.entity.UcenterMember;
import com.cwx.educenter.entity.vo.RegisterVo;
import com.cwx.educenter.service.UcenterMemberService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2020-08-11
 */
@RestController
@RequestMapping("/educenter/member")
@CrossOrigin
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService ucenterMemberService;

    //电话和密码登录
    @PostMapping("/login")
    @ApiOperation("登录，返回token")
    public R login(@RequestBody UcenterMember ucenterMember){
        String token = ucenterMemberService.login(ucenterMember);
        return R.ok().data("token", token);
    }

    //电话、密码、验证码、昵称进行注册
    @PostMapping("/register")
    @ApiOperation("注册")
    public R register(@RequestBody RegisterVo registerVo){
        ucenterMemberService.register(registerVo);
        return R.ok();
    }

    @GetMapping("/getMemberInfo")
    @ApiOperation("根据token获取用户信息")
    public R getMemberInfo(HttpServletRequest request){
        //调用jwt工具类，获取头部信息，返回用户id
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        //查询数据库根据id获得用户信息
        UcenterMember member = ucenterMemberService.getById(memberId);
        return R.ok().data("userInfo", member);
    }

    //根据用户id获取客户信息
    @PostMapping("/getUserInfoOrder/{id}")
    public UcenterMemberOrder getUserInfoOrder(@PathVariable String id){
        UcenterMember ucenterMember = ucenterMemberService.getById(id);
        //把UcenterMember复制为UcenterMemberOrder对象
        UcenterMemberOrder ucenterMemberOrder = new UcenterMemberOrder();
        BeanUtils.copyProperties(ucenterMember,ucenterMemberOrder);
        return ucenterMemberOrder;
    }

    @GetMapping("/countRegister/{day}")
    public R countRegister(@PathVariable String day){
        Integer count = ucenterMemberService.ucenterMemberService(day);
        return R.ok().data("countRegister",count);
    }

}


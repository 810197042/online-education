package com.cwx.serviceedu.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cwx.commonutils.R;
import com.cwx.serviceedu.entity.AclUser;
import com.cwx.serviceedu.service.AclUserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/***
 @author  *
 @date 2023/10/23$ 21:01$*
 @description: */
@Api(description = "登录")
@RestController
//@CrossOrigin
@RequestMapping("/eduservice/user")
public class AclUserController {

    @Autowired
    AclUserService userService;

    //login
    @PostMapping("/login")
    public R login(@RequestBody AclUser user) {
        if ("admin".equals(user.getUsername()) && "admin".equals(user.getPassword())) {
            return R.ok().data("token", "admin");
        }
        return R.error();
    }

    //info
    @GetMapping("/info")
    public R info() {
        return R.ok().data("roles", "admin").data("name", "admin").data("avatar", "https://t7.baidu.com/it/u=2604797219,1573897854&fm=193&f=GIF");
    }

    @PostMapping("/logout")
    public R logout() {
        return R.ok();
    }

}

package com.cwx.serviceedu.controller;

import com.cwx.commonutils.R;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

/***
 @author  *
 @date 2023/10/23$ 21:01$*
 @description: */
@Api(description = "登录")
@RestController
//@CrossOrigin
@RequestMapping("/eduservice/user")
public class EduLoginController {

    //login
    @PostMapping("/login")
    public R login() {
        return R.ok().data("token", "admin");
    }

    //info
    @GetMapping("/info")
    public R info() {
        return R.ok().data("roles", "casdasd").data("username", "abc").data("avatar", "https://t7.baidu.com/it/u=2604797219,1573897854&fm=193&f=GIF");
    }
}

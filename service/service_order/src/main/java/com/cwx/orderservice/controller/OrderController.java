package com.cwx.orderservice.controller;



import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cwx.commonutils.feignclient.EduClient;
import com.cwx.commonutils.feignclient.UcenterClient;
import com.cwx.commonutils.user.CourseWebVoOrder;
import com.cwx.commonutils.user.UcenterMemberOrder;
import com.cwx.orderservice.entity.Order;
import com.cwx.orderservice.service.OrderService;
import com.cwx.commonutils.JwtUtils;
import com.cwx.commonutils.R;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2020-08-16
 */
@RestController
@RequestMapping("/edeorder/order")
//@CrossOrigin
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private EduClient eduClient;

    @Autowired
    private UcenterClient ucenterClient;

    @PostMapping("/createOrder/{courseId}")
    @ApiOperation("根据课程id生成订单")
    public R createOrder(@PathVariable String courseId, HttpServletRequest request) {
        String orderId = orderService.createOrders(courseId, JwtUtils.getMemberIdByJwtToken(request));
        return R.ok().data("orderId", orderId);
    }

    @GetMapping("/getOrderInfo/{orderNo}")
    @ApiOperation("根据订单号查订单信息")
    public R getOrderInfo(@PathVariable String orderNo) {
        LambdaQueryWrapper<Order> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Order::getOrderNo, orderNo);
        Order one = orderService.getOne(lambdaQueryWrapper);
        return R.ok().data("item", one);
    }

    @GetMapping("/isBuyCourse/{courseId}/{memberId}")
    @ApiOperation("根据课程id和用户id查询订单表中订单状态")
    public boolean isBuyCourse(@PathVariable("courseId") String courseId, @PathVariable("memberId") String memberId) {
        boolean isBuy = orderService.isBuyCourse(courseId, memberId);
        return isBuy;
    }


}


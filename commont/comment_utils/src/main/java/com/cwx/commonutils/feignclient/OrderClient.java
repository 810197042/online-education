package com.cwx.commonutils.feignclient;

import com.cwx.commonutils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("service-order")
public interface OrderClient {

    @GetMapping("/edeorder/order/isBuyCourse/{courseId}/{memberId}")
    @ApiOperation("根据课程id和用户id查询订单表中订单状态")
    public boolean isBuyCourse(@PathVariable("courseId") String courseId, @PathVariable("memberId") String memberId);

}

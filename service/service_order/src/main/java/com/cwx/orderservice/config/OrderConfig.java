package com.cwx.orderservice.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.atguigu.orderservice.mapper")
public class OrderConfig {

}

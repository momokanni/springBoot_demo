package com.txhl.wxorder;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@MapperScan(basePackages = "com.txhl.wxorder.entity.mapper")
@EnableCaching
public class WxorderApplication {

	public static void main(String[] args) {
		SpringApplication.run(WxorderApplication.class, args);
	}
}

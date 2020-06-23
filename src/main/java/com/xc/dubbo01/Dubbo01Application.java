package com.xc.dubbo01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ServletComponentScan(basePackages = {"com.xc.dubbo01.listener","com.xc.dubbo01.filter"})
public class Dubbo01Application {

	public static void main(String[] args) {
		SpringApplication.run(Dubbo01Application.class, args);
	}

}

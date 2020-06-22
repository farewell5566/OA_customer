package com.xc.dubbo01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class Dubbo01Application {

	public static void main(String[] args) {
		SpringApplication.run(Dubbo01Application.class, args);
	}

}

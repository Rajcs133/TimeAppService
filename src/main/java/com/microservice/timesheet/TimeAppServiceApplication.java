package com.microservice.timesheet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.microservice.timesheet")
public class TimeAppServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TimeAppServiceApplication.class, args);
	}

}

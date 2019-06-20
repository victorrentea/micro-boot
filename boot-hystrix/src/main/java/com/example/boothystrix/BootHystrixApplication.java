package com.example.boothystrix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@SpringBootApplication
@EnableHystrixDashboard
public class BootHystrixApplication {

	public static void main(String[] args) {
		SpringApplication.run(BootHystrixApplication.class, args);
	}

}


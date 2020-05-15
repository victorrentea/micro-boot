package com.example.booteureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class BootEurekaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BootEurekaServerApplication.class, args);
	}
}

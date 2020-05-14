package com.example.bootservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

//@EnableBinding(Sink.class)
@SpringBootApplication
//@EnableEurekaClient
public class BootServiceApplication {
	
	@Autowired
	private DuumyDataCreator creator;
	
	@PostConstruct
	public void stuff() {
		creator.insertStuff();
	}

	public static void main(String[] args) {
		SpringApplication.run(BootServiceApplication.class, args);
	}
}

@RestController
class MyResource {
	@Value("${message}")
	private String message;

	@GetMapping("hello")
	public String hello() {
		return "Hello " + message;
	}

}
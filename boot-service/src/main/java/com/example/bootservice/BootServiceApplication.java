package com.example.bootservice;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableBinding(Sink.class)
@SpringBootApplication
@EnableEurekaClient
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
@MessageEndpoint
class ReservationCreator {
	@Autowired
	private ReservationRestRepository repo;
	
	@ServiceActivator(inputChannel = "input")
	public void acceptNewReservation(String rn) {
		System.out.println("Message received.");
		repo.save(new Reservation(rn));
	}
}

@RefreshScope
@RestController
class MessageController {
	@Value("${message}")
	private String message;
	
	@GetMapping("message")
	public String getMessage() {
		return message;
	}
}

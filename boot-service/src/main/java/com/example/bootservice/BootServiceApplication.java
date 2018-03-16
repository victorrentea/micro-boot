package com.example.bootservice;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;

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

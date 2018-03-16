package com.example.bootclient;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpMethod;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import lombok.Data;

@RestController
@RequestMapping("/reservation")
public class ReservationClient {
	
	@Autowired
	private RestTemplate restTemplate;
	
	public List<String> noop() {
		return emptyList();
	}
	
	@HystrixCommand(defaultFallback = "noop")
	@GetMapping
	public List<String> getReservationNames() {
		return restTemplate.exchange("http://boot-service/reservation", 
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<Resources<ReservationDto>>() {
				})
				.getBody()
				.getContent()
				.stream()
				.map(ReservationDto::getName)
				.collect(toList())
				;
	}
	
	@Autowired
	private Source channel;
	
	@PostMapping
	public void createReservation(@RequestBody ReservationDto r) {
		Message<String> message = MessageBuilder.withPayload(r.getName()).build();
		channel.output().send(message);
		System.out.println("Message Sent");
	}
}

@Data
class ReservationDto {
	private Long id;
	private String name;
}

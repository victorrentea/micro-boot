package com.example.bootclient;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

import java.util.List;

import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/reservationNames")
public class ReservationClient {
	// TODO HystrixCommand

	@Autowired
	private RestTemplate rest;
	@GetMapping
	public List<String> getReservationNames() {
		// TODO implement me with restTemplate.exchange
		// Hint: PTReference
		return rest.exchange("http://boot-service/reservations", HttpMethod.GET,
						null, new ParameterizedTypeReference<Resources<ReservationDto>>() {
						})
				.getBody()
				.getContent()
				.stream()
				.map(ReservationDto::getName)
				.collect(toList());
	}
	
}

@Data
class ReservationDto {
	private String name;
}
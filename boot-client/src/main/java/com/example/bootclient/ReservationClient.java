package com.example.bootclient;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.core.ParameterizedTypeReference;
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
public class ReservationClient {
    private RestTemplate restTemplate = new RestTemplate();
//    @Value("${server.port}")

    @GetMapping("/dishes/special")
    public String dishes() {
        ResponseEntity<DishDto> dishResponse =
                restTemplate.getForEntity("http://localhost:8080/dish/{id}",
                DishDto.class, 13);
        return dishResponse.getBody().name;
    }
    @GetMapping("/reservationNames")
    public List<String> getReservationNames() {

        return emptyList();
    }
}

class DishDto {
    public String name;
    public boolean vegetarian;
}
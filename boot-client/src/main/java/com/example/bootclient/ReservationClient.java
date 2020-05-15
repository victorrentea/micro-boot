package com.example.bootclient;

import static java.util.stream.Collectors.toList;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resources;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ReservationClient/*<String>*/ {
    private final RestTemplate restTemplate;

    public ReservationClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

//    public String noop() {
//        return "e mort. mai stai o tzara";
//    }

//    @HystrixCommand(defaultFallback = "noop")
    @GetMapping("/dishes/special")
    public String dishes() throws URISyntaxException {
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("Authorization", "Basic YWRtaW46YWRtaW4=");
        RequestEntity<String> entity = new RequestEntity<>(headers, HttpMethod.GET, new URI("http://boot-service/dish/13"));
        ResponseEntity<DishDto> dishResponse = restTemplate.exchange(entity,DishDto.class);
        return dishResponse.getBody().name;
    }



    @GetMapping("/reservationNames")
    public List<String> getReservationNames(/*List<String >x*/) {
        return restTemplate.exchange("http://boot-service/reservations",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Resources<ReservationDto>>(){}
                )
            .getBody()
            .getContent()
            .stream()
            .map(ReservationDto::getName)
            .collect(toList())
        ;
    }
}

class ReservationDto {
    private String name;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}

class DishDto {
    public String name;
    public boolean vegetarian;
}
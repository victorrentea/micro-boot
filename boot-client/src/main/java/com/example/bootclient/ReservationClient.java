package com.example.bootclient;

import static java.util.stream.Collectors.toList;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resources;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.xml.bind.DatatypeConverter;

@RestController
public class ReservationClient/*<String>*/ {
    private final RestTemplate restTemplate;
    @Value("${jwt.secret:secret}")
    private String jwtSecret;

    public ReservationClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

//    public String noop() {
//        return "e mort. mai stai o tzara";
//    }

//    @HystrixCommand(defaultFallback = "noop")
    @GetMapping("/dishes/special")
    public String dishes() throws URISyntaxException {

        String jwtEncoded = Jwts.builder()
                .setSubject("user")
                .signWith(SignatureAlgorithm.HS512, DatatypeConverter.parseBase64Binary(jwtSecret))
                .claim("country", "Romanica")
                .compact();

        System.out.println(jwtEncoded);

        MultiValueMap<String, String> headers = new HttpHeaders();
//        headers.add("Authorization", "Basic YWRtaW46YWRtaW4=");
//        headers.add("Authorization", "Bearer " + jwtEncoded); //standard
        headers.add("JWT-Header", jwtEncoded); //standard
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
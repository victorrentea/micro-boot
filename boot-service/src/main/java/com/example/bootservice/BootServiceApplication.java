package com.example.bootservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Component;
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

@Component
class B {
	private final MyResource myResource;

	public B(MyResource myResource) {
		this.myResource = myResource;
	}

	@PostConstruct
	public void m() {
		System.out.println("Ma creez si eu");
	}
}

@RefreshScope
@RestController
class MyResource {
	@Value("${message}")
	private String message;

	@GetMapping("hello")
	public String hello() {
		return "Hello " + message;
	}

}

@RepositoryRestResource(path="reservations")
interface ReservationRestRepo extends JpaRepository<Reservation, Long> {
}
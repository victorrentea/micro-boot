package com.example.bootservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.PostConstruct;
import java.io.File;

//@EnableBinding(Sink.class)
@EnableSwagger2
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

	private static final Logger log = LoggerFactory.getLogger(MyResource.class);
	@Value("${message}")
	private String message;

//	DE CE AVEM NEVOIE SA RE-CREEM INSTANTE la REINJECTIE:
//	@Value("${input.folder.poller}")
//	private File folder;
//	@PostConstruct
//	public void folderExists() {
//		if (!folder.isDirectory()) {
//			throw new IllegalArgumentException("Not a folder: " + folder);
//		}
//	}

	@GetMapping("hello")
	public String hello() {
		return "Hello " + message;
	}

	@GetMapping("dish/{id}")
	public DishDto getDish(@PathVariable long id) {
		log.info("Handling request");
		return new DishDto("Dish " + id, false, "ce-ai in frigider");
	}
	@PostMapping("dish")
	public void getDish(@RequestBody DishDto dishDto) {
		System.out.println("NIMIC");
	}
}

class DishDto {
	public String name;
	public boolean vegetarian;
	public String ingrediente;

	public DishDto(String name, boolean vegetarian, String ingrediente) {
		this.name = name;
		this.vegetarian = vegetarian;
		this.ingrediente = ingrediente;
	}
}


@RepositoryRestResource(path="reservations")
interface ReservationRestRepo extends JpaRepository<Reservation, Long> {
}
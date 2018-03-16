package com.example.bootservice;

import java.util.stream.Stream;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DuumyDataCreator {

	@Autowired
	private EntityManager em;
	
	@Transactional
	public void insertStuff() {
		Stream.of("John","Mike","Maria").map(Reservation::new).forEach(em::persist);
	}
}

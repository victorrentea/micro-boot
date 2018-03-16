package com.example.bootservice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path="/reservation")
public interface ReservationRestRepository extends JpaRepository<Reservation, Long> {

}

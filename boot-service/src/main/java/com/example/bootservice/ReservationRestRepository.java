package com.example.bootservice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
// It's a repository AND a Rest Resource at the same time
public interface ReservationRestRepository extends JpaRepository<Reservation, Long> {

}

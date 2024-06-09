package com.example.service1.repositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository ;
import com.example.service1.Entity1 ;
import org.springframework.stereotype.Repository ;

import java.util.UUID;

@Repository
public interface Service1Repository extends ReactiveCrudRepository<Entity1, UUID> {
}

package com.example.service5.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.service5.Entity5;

import java.util.UUID;

@Repository
public interface Service5Repository extends JpaRepository<Entity5,Long>{
    Entity5 findByIdEntity1(UUID idEntity1);
    
}
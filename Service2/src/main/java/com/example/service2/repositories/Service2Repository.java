package com.example.service2.repositories;

import org.springframework.stereotype.Repository;
import com.example.service2.Entity2;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

@Repository
public interface Service2Repository extends JpaRepository<Entity2,Long> {
    public void deleteByIdEntity1(UUID idEntity1);
    public Entity2 findByIdEntity1(UUID idEntity1);
}
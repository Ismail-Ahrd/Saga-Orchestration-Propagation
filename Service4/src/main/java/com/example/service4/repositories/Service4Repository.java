package com.example.service4.repositories;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import com.example.service4.Entity4;

import java.util.UUID;

@Repository
public interface Service4Repository extends JpaRepository<Entity4,Long>{

    public void deleteByIdEntity1(UUID idEntity1);
    Entity4 findByIdEntity1(UUID idEntity1);
}
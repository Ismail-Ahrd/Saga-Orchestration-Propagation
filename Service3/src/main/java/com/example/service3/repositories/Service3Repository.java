package com.example.service3.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.service3.Entity3;
import java.util.List;
import java.util.UUID;


@Repository
public interface Service3Repository extends JpaRepository<Entity3, Long> {
    public void deleteByIdEntity1(UUID idEntity1);
    Entity3 findByIdEntity1(UUID idEntity1);

}
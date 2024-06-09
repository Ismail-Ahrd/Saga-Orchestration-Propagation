package com.example.service4;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.UUID;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@Entity
public class Entity4 {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private UUID idEntity1;

    public Entity4(String name,UUID idEntity1) {
        this.name = name;
        this.idEntity1 = idEntity1;
    }
}

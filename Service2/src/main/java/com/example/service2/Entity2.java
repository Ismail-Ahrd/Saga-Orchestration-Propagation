package com.example.service2;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@AllArgsConstructor
public class Entity2 {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String name;
    private UUID idEntity1;

    public Entity2(String name,UUID idEntity1){
        this.name=name ;
        this.idEntity1 = idEntity1 ;
    }
}



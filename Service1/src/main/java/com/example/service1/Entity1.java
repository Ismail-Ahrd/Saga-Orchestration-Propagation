package com.example.service1;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ENTITY1")
public class Entity1 {
    
    @Id
    private UUID id;
    private String name;
    private Status status = Status.PENDING;
    
    public Entity1(String name ){
        this.name=name ;
    }
    
}


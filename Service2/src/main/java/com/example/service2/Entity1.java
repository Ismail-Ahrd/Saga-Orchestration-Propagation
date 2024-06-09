package com.example.service2;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor 
public class Entity1 {
    private UUID id;;
    private String name;

    public Entity1(String name){
        this.name=name ;
    }
}

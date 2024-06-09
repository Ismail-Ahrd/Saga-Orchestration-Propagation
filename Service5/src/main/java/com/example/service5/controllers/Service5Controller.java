package com.example.service5.controllers;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

import com.example.service5.Entity5;
import org.springframework.beans.factory.annotation.Autowired ;
import com.example.service5.repositories.Service5Repository;
import com.example.service5.Entity1;

@RestController
@RequestMapping("service5")
public class Service5Controller {

    @Autowired
    private Service5Repository service5Repository;

    @PostMapping
    public Boolean create(@RequestBody Entity1  entity1){
        System.out.println("call to service 5");

        if (entity1.getName().equals("service5")) return false;
        Entity5 entity5 = new Entity5(entity1.getName(), entity1.getId());
        service5Repository.save(entity5);
        return true; 
    }

    @GetMapping
    public List<Entity5> getAll(){
        return service5Repository.findAll();
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id){
        Long id1 = this.service5Repository.findByIdEntity1(id).getId();
        service5Repository.deleteById(id1);
    }


        
}
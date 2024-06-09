package com.example.service4.controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.service4.Entity4;
import com.example.service4.repositories.Service4Repository;
import java.util.List;
import java.util.UUID;

import com.example.service4.Entity1;


@RestController
@RequestMapping("service4")
public class Service4Controller {
     @Autowired
    private  Service4Repository service4Repository;

    @PostMapping
    public Boolean create(@RequestBody Entity1 entity1){
        System.out.println("call to service 4");
        if (entity1.getName().equals("service4")) return false;
        Entity4 entity4 = new Entity4(entity1.getName(), entity1.getId());
        service4Repository.save(entity4);
        return true;  
    }
    
    @GetMapping
    public List<Entity4> getAll(){
        return service4Repository.findAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id){
        Long id1 = this.service4Repository.findByIdEntity1(id).getId();
        service4Repository.deleteById(id1);
    }

   
}
package com.example.service2.controllers;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired ;
import com.example.service2.repositories.Service2Repository ;
import com.example.service2.Entity2;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

import com.example.service2.Entity1;
@RestController
@RequestMapping("service2")
public class Service2Controller {
    @Autowired
    private Service2Repository service2Repository ;

    @PostMapping
    public Boolean create(@RequestBody Entity1 entity1){
        if (entity1.getName().equals("service2")) return false;
        Entity2 entity2 = new Entity2(entity1.getName(),entity1.getId());
        service2Repository.save(entity2);
        return true;
    }

    @GetMapping
    public List<Entity2> getAll(){
        return service2Repository.findAll();
    }
    
    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id){
        //System.out.println(this.service2Repository.findByIdEntity1(id).getId());
        Long id1 = this.service2Repository.findByIdEntity1(id).getId();
        service2Repository.deleteById(id1);

    }

    
}
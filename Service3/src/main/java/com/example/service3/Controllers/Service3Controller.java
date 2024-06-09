package com.example.service3.Controllers;

import com.example.service3.repositories.Service3Repository;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

import com.example.service3.Entity3;
import org.springframework.beans.factory.annotation.Autowired ;
import com.example.service3.Entity1;

@RestController
@RequestMapping("service3")
public class Service3Controller {
    @Autowired
    private Service3Repository service3Repository;

    @PostMapping
    public Boolean create(@RequestBody Entity1 entity1){
        if (entity1.getName().equals("service3")) return false;
        Entity3 entity3 = new Entity3(entity1.getName(), entity1.getId());
        service3Repository.save(entity3);
        return true;  
    }

    @GetMapping
    public List<Entity3> getAll(){
        return service3Repository.findAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id){
        Long id1 = this.service3Repository.findByIdEntity1(id).getId();
        service3Repository.deleteById(id1);
    }
    
}
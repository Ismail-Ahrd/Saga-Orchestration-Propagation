package com.example.service1.controllers;

import com.example.service1.Status;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Flux;
import com.example.service1.Entity1;
import com.example.service1.repositories.Service1Repository ;
import lombok.AllArgsConstructor ;
import org.springframework.context.annotation.Bean ;

import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Supplier;

@RestController
@AllArgsConstructor
@RequestMapping("service1")
public class Service1Controller {
   
    private Service1Repository service1Repository ;
    private Sinks.Many<Entity1> sink;

    @PostMapping
    public Mono<Entity1> create(@RequestBody Mono<String> mono){
        return mono
                .flatMap((String t) ->service1Repository.save(new Entity1(t))
                ).doOnNext((Entity1 t) ->  this.emitEvent(t)) ;
        
    }

    private void emitEvent(Entity1 entity1){
        this.sink.tryEmitNext(entity1);
    }
    
    
    private Flux<Entity1> flux;   

    @Bean
    public Supplier<Flux<Entity1>> supplier(){
        return () -> flux;
    };

   @Bean
    public Consumer<Flux<Entity1>> consumer() {
        return flux -> flux
                .doOnNext(entity -> System.out.println("Consuming :: " + entity))
                .flatMap(this::delete)
                .subscribe();
    }

    public Mono<Void> delete(Entity1 entity){
        if (entity.getStatus() == Status.FAILED) {
            return this.service1Repository.deleteById(entity.getId()) ;
        } else {
            System.out.println(entity.getStatus());
            return this.service1Repository.save(entity).then(Mono.empty());
        }

    }
    
    @GetMapping
    public Flux<Entity1> getAll(){
        return service1Repository.findAll();
    }

}

package com.example.service1.config;
import com.example.service1.Entity1;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Configuration
public class Entity1Config {

    @Bean
    public Sinks.Many<Entity1> sink(){
        return Sinks.many().unicast().onBackpressureBuffer();
    }

    @Bean
    public Flux<Entity1> flux(Sinks.Many<Entity1> sink){
        return sink.asFlux();
    }

}

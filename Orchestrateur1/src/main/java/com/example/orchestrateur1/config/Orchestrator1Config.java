package com.example.orchestrateur1.config;

import com.example.orchestrateur1.service.OrchestratorService ;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import com.example.orchestrateur1.entities.Entity1 ;
import java.util.function.Function;
import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class Orchestrator1Config {
    private final OrchestratorService orchestratorService;

    @Bean
    public Function<Flux<Entity1>, Flux<Entity1>> processor(){
        return (Flux<Entity1> flux) -> flux.flatMap(entity -> this.orchestratorService.startSaga(entity));
    }
}

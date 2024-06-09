package com.example.orchestrateur2.controller;

import com.example.orchestrateur2.entities.Entity1;
import com.example.orchestrateur2.service.OrchestratorService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("orchestrator2")
public class Orchestrateur2Controller {
    @Value("${propagation.type}")
    private  Boolean included;

    private OrchestratorService orchestratorService;

    public Orchestrateur2Controller(OrchestratorService orchestratorService) {
        this.orchestratorService = orchestratorService;
    }

    @PostMapping
    public Mono<Boolean> start(@RequestBody Entity1 entity1) {
        System.out.println("call to orchestrator2");
        return this.orchestratorService.startSaga(entity1)
                .map(aBoolean -> {
                    if (included) {
                        return aBoolean;
                    }
                    else{
                        return true;
                    }
                });
    }

    @PostMapping("/revert")
    public Mono<Void> revert(@RequestBody Entity1 entity1) {
        return this.orchestratorService.revert(entity1).then(Mono.empty());

    }

    @GetMapping
    public String test() {
      return "oussam nadi" ;
    }
}

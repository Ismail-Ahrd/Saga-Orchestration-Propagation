package com.example.orchestrateur1.service.steps;

import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import com.example.orchestrateur1.service.WorkflowStep;
import com.example.orchestrateur1.service.WorkflowStepStatus;
import com.example.orchestrateur1.entities.Entity1;


public class StepOrchestrateur2 implements WorkflowStep{
     private final WebClient webClient;
    private WorkflowStepStatus stepStatus = WorkflowStepStatus.PENDING;
    private Entity1 entity1 ;
    
    public StepOrchestrateur2(WebClient webClient, Entity1 entity1) {
        this.webClient = webClient;
        this.entity1=entity1;
    }

    @Override
    public Mono<Boolean> process() {
        System.out.println("process orchestrator2");
        return this.webClient
                .post()
                .uri("/orchestrator2")
                .body(BodyInserters.fromValue(this.entity1))
                .retrieve()
                .bodyToMono(Boolean.class)
                .doOnNext(b -> this.stepStatus = b ? WorkflowStepStatus.COMPLETE : WorkflowStepStatus.FAILED);
    }

    @Override
    public WorkflowStepStatus getStatus() {
        return this.stepStatus;
    }

    @Override
    public Mono<Boolean> revert() {
        return this.webClient
                .post()
                .uri("/orchestrator2/revert")
                .body(BodyInserters.fromValue(this.entity1))
                .retrieve()
                .bodyToMono(Void.class)
                .thenReturn(true)
                .onErrorReturn(false);
    }

}
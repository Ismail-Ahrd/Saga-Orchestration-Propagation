package com.example.orchestrateur1.service.steps;

import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import com.example.orchestrateur1.service.WorkflowStep;
import com.example.orchestrateur1.service.WorkflowStepStatus;
import com.example.orchestrateur1.entities.Entity1;

public class StepService2 implements WorkflowStep{
    private final WebClient webClient;
    private WorkflowStepStatus stepStatus = WorkflowStepStatus.PENDING;
    private Entity1 entity1 ;
    
    public StepService2(WebClient webClient, Entity1 entity1) {
        this.webClient = webClient;
        this.entity1=entity1;
    }

    @Override
    public Mono<Boolean> process() {
        return this.webClient
                .post()
                .uri("/service2")
                .body(BodyInserters.fromValue(this.entity1))
                .retrieve()
                .bodyToMono(Boolean.class)
                .map(aBoolean -> {
                    System.out.println("boolean from process "+aBoolean) ;
                    this.stepStatus = aBoolean ? WorkflowStepStatus.COMPLETE : WorkflowStepStatus.FAILED ;
                    System.out.println("workflow step "+this.stepStatus) ;

                    return aBoolean ;
                });

    }

    @Override
    public WorkflowStepStatus getStatus() {
        return this.stepStatus;
    }

    @Override
    public Mono<Boolean> revert() {
        return this.webClient
                .delete()
                .uri("/service2/" + this.entity1.getId())
                .retrieve()
                .bodyToMono(Void.class)
                .map(response -> true)
                .onErrorReturn(false);
    }

    
}
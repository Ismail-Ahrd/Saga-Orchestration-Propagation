package com.example.orchestrateur2.service.steps;

import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import com.example.orchestrateur2.service.WorkflowStep;
import com.example.orchestrateur2.service.WorkflowStepStatus;
import com.example.orchestrateur2.entities.Entity1;

public class StepService5 implements WorkflowStep{
    private final WebClient webClient;
    private WorkflowStepStatus stepStatus = WorkflowStepStatus.PENDING;
    private Entity1 entity1 ;
    
    public StepService5(WebClient webClient, Entity1 entity1) {
        this.webClient = webClient;
        this.entity1=entity1;
    }

    @Override
    public Mono<Boolean> process() {
        return this.webClient
                .post()
                .uri("/service5")
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
                .delete()
                .uri("/service5/" + this.entity1.getId())
                .retrieve()
                .bodyToMono(Void.class)
                .map(response -> true)
                .onErrorReturn(false);
    }

    
}
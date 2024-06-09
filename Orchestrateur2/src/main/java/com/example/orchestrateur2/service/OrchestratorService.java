package com.example.orchestrateur2.service ;

import com.example.orchestrateur2.service.steps.StepService4;
import com.example.orchestrateur2.service.steps.StepService5;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service ;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.orchestrateur2.entities.Entity1;
import reactor.core.publisher.Mono;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import reactor.core.publisher.Flux;

import java.util.List;


@Service
public class OrchestratorService {
    
    @Autowired
    @Qualifier("service4")
    private WebClient service4Client;

    @Autowired
    @Qualifier("service5")
    private WebClient service5Client;
private Workflow orchestrator2Workflow ;
    public Mono<Boolean> startSaga(final Entity1 entity1) {
        this.orchestrator2Workflow = this.getOrchestrator2Workflow(entity1);
        return Flux.fromStream(() -> orchestrator2Workflow.getSteps().stream())
                .flatMap(WorkflowStep::process)
                .collectList()
                .flatMap(results -> {
                    boolean allSuccess = results.stream().allMatch(Boolean::booleanValue);
                    if (allSuccess) {
                        return Mono.just(true);
                    } else {
                        return Mono.error(new WorkflowException("orchestrator2 flow failed!"));
                    }
                })
                .onErrorResume(ex -> this.revert(entity1).thenReturn(false));
    }



    private Workflow getOrchestrator2Workflow(Entity1 entity1){
        WorkflowStep stepService4 = new StepService4(this.service4Client, entity1);
        WorkflowStep stepService5 = new StepService5(this.service5Client, entity1);
        return new WorkflowImpl(List.of(stepService4, stepService5));
    }

    public Mono<Boolean> revert(Entity1 entity1) {
        return Flux.fromStream(() -> this.orchestrator2Workflow.getSteps().stream())
                .map(workflowStep -> {
                    System.out.println("workflow step before" +workflowStep.getClass()+","+workflowStep.getStatus());
                    return workflowStep ;
                })
                .filter(wf -> wf.getStatus().equals(WorkflowStepStatus.COMPLETE))

                .map(workflowStep -> {
                    System.out.println("workflow step after" +workflowStep.getClass()+","+workflowStep.getStatus());
                    return workflowStep ;
                })
                .flatMap(WorkflowStep::revert)
                .retry(3)
                .then(Mono.just(false));
    }

    private Boolean revertOrchestrator1(Entity1 entity1){
        return true ;
    }
}
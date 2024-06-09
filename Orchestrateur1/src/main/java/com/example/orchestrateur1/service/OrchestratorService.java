package com.example.orchestrateur1.service ;

import com.example.orchestrateur1.entities.Status;
import com.example.orchestrateur1.service.steps.StepOrchestrateur2;
import com.example.orchestrateur1.service.steps.StepService2;
import com.example.orchestrateur1.service.steps.StepService3;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service ;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.orchestrateur1.entities.Entity1;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.ArrayList;
import java.util.List;


@Service
public class OrchestratorService {
    
    @Autowired
    @Qualifier("service2")
    private WebClient service2Client;

    @Autowired
    @Qualifier("service3")
    private WebClient service3Client;


    @Autowired
    @Qualifier("orchestrator2")
    private WebClient orchestrator2Client;
    private Workflow orchestrator1Workflow ;
    @Value("${propagation.type}")
    private  Boolean included;

    public Mono<Entity1> startSaga(final Entity1 entity1) {
        this.orchestrator1Workflow = this.getOrchestrator1Workflow(entity1);
        return Flux.fromStream(() -> orchestrator1Workflow.getSteps().stream())
                .flatMap(WorkflowStep::process)
                .collectList()
                .flatMap(results -> {
                    boolean allSuccess = results.stream().allMatch(Boolean::booleanValue);
                    if (allSuccess) {
                        entity1.setStatus(Status.COMPLETED);
                        return Mono.just(entity1);
                    } else {
                        entity1.setStatus(Status.FAILED);
                        return Mono.error(new WorkflowException("orchestrator1 flow failed!"));
                    }
                })
                .onErrorResume(ex -> {
                    System.out.println("exception handling");
                    return this.revert(entity1).thenReturn(entity1);
                });
    }



    private Workflow getOrchestrator1Workflow(Entity1 entity1){
        WorkflowStep stepService2 = new StepService2(this.service2Client, entity1);
        WorkflowStep stepService3 = new StepService3(this.service3Client, entity1);
        WorkflowStep stepOrchestrator2 = new StepOrchestrateur2(this.orchestrator2Client, entity1);
        return new WorkflowImpl(List.of(stepService2, stepService3, stepOrchestrator2));
    }

    public Mono<Boolean> revert( Entity1 entity1) {
        System.out.println("inside revert");

        List<WorkflowStep> steps = new ArrayList<>(this.orchestrator1Workflow.getSteps());
        if(!included){
            System.out.println("not included");
            System.out.println(this.orchestrator1Workflow.getSteps());
            steps.remove(2);
            steps.stream().map((wf) -> {
                System.out.println("step after removing orchestrator2" +wf.getClass());
                return wf;
            });
        }
        return Flux.fromStream(() -> steps.stream())
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
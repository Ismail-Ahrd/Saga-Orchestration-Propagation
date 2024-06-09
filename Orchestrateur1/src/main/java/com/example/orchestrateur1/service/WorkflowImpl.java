package com.example.orchestrateur1.service;
import lombok.Setter;

import java.util.List;
@Setter
public class WorkflowImpl implements Workflow{
    private  List<WorkflowStep> steps;

    public WorkflowImpl(List<WorkflowStep> steps) {
        this.steps = steps;
    }

    @Override
    public List<WorkflowStep> getSteps() {
        return this.steps;
    }



}
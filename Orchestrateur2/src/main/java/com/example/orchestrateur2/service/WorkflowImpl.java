package com.example.orchestrateur2.service;
import java.util.List;

public class WorkflowImpl implements Workflow{
    private final List<WorkflowStep> steps;

    public WorkflowImpl(List<WorkflowStep> steps) {
        this.steps = steps;
    }

    @Override
    public List<WorkflowStep> getSteps() {
        return this.steps;
    }

}
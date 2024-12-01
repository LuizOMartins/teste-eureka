package com.eureka.testeeureka.dto;

import java.util.List;
import java.util.stream.Collectors;
import com.eureka.testeeureka.model.Step;
import com.eureka.testeeureka.model.Workflow;

public class WorkflowDTO {
    private Long id;
    private String name;
    private String description;

    private List<String> steps;

    public WorkflowDTO(Workflow workflow) {
        this.id = workflow.getId();
        this.name = workflow.getName();
        this.description = workflow.getDescription();
        this.steps = workflow.getSteps().stream()
                .map(Step::getName)
                .collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getSteps() {
        return steps;
    }

    public void setSteps(List<String> steps) {
        this.steps = steps;
    }
}

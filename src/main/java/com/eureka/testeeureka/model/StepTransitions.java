package com.eureka.testeeureka.model;

import jakarta.persistence.*;

@Entity
public class StepTransitions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "from_step_id")
    private Step fromStep;

    @ManyToOne
    @JoinColumn(name = "to_step_id")
    private Step toStep;

    @ManyToOne
    @JoinColumn(name = "workflow_id")
    private Workflow workflow;

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Step getFromStep() {
        return fromStep;
    }

    public void setFromStep(Step fromStep) {
        this.fromStep = fromStep;
    }

    public Step getToStep() {
        return toStep;
    }

    public void setToStep(Step toStep) {
        this.toStep = toStep;
    }

    public Workflow getWorkflow() {
        return workflow;
    }

    public void setWorkflow(Workflow workflow) {
        this.workflow = workflow;
    }
}
package com.eureka.testeeureka.model;


import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import jakarta.persistence.*;

@Entity
public class Step {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "workflow_id", nullable = false)
    private Workflow workflow;

    @OneToMany(mappedBy = "step", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<StepReviews> stepReviews = new ArrayList<>();

    private String name;

    private String description;

    private String roleRequired;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Workflow getWorkflow() {
        return workflow;
    }

    public void setWorkflow(Workflow workflow) {
        this.workflow = workflow;
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

    public String getRoleRequired() {
        return roleRequired;
    }

    public void setRoleRequired(String roleRequired) {
        this.roleRequired = roleRequired;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public List<StepReviews> getStepReviews() {
        return stepReviews;
    }

    public void setStepReviews(List<StepReviews> stepReviews) {
        this.stepReviews = stepReviews;
    }

    @Override
    public String toString() {
        return "Step{" +
                "id=" + id +
                ", workflow=" + workflow +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", roleRequired='" + roleRequired + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}

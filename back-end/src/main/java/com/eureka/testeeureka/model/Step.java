package com.eureka.testeeureka.model;


import jakarta.persistence.*;
import java.util.Date;

@Entity
public class Step {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "workflow_id", nullable = false)
    private Workflow workflow;

    private String name;

    private String description;

    private String roleRequired;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();

    // Getters e Setters
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
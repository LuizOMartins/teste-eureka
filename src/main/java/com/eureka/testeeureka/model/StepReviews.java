package com.eureka.testeeureka.model;
import jakarta.persistence.*;
import java.util.Date;


import jakarta.persistence.*;
import java.util.Date;

@Entity
public class StepReviews {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "step_id", nullable = true)
    private Step step;

    @ManyToOne
    @JoinColumn(name = "script_id", nullable = true)
    private Script script;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true)
    private Users user;

    @Column(nullable = false, length = 50)
    private String status;

    @Column(columnDefinition = "TEXT")
    private String comment;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = true, updatable = false)
    private Date createdAt = new Date();

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Step getStep() {
        return step;
    }

    public void setStep(Step step) {
        this.step = step;
    }

    public Script getScript() {
        return script;
    }

    public void setScript(Script script) {
        this.script = script;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "StepReviews{" +
                "id=" + id +
                ", step=" + step +
                ", script=" + script +
                ", user=" + user +
                ", status='" + status + '\'' +
                ", comment='" + comment + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
package com.eureka.testeeureka.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "step_reviews", schema = "eureka_test")
public class StepReviews {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "step_id", nullable = true)
    private Step step;

    @Column(name = "script_id", nullable = true)
    private Long scriptId;

    @Column(name = "user_id", nullable = true)
    private Long userId;

    @Column(nullable = false, length = 50)
    private String status;

    @Column(name = "comment", columnDefinition = "TEXT")
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

    public Long getScriptId() {
        return scriptId;
    }

    public void setScriptId(Long scriptId) {
        this.scriptId = scriptId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
                ", scriptId=" + scriptId +
                ", userId=" + userId +
                ", status='" + status + '\'' +
                ", comment='" + comment + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}

package com.eureka.testeeureka.service;

import java.util.List;
import com.eureka.testeeureka.model.Step;
import com.eureka.testeeureka.model.Workflow;

public interface WorkflowService {
    List<Workflow> findAll();
    Workflow findById(Long id);
    Workflow save(Workflow workflow);
    void deleteById(Long id);
    Step getNextStep(Long fromStepId);
    List<Step> findAllByWorkflowId(Long workflowId);
    void analysisStep(Workflow workflow, Long userId, String comment, String userRole, boolean isApproved);
}

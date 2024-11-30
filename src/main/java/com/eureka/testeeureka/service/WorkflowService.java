package com.eureka.testeeureka.service;

import com.eureka.testeeureka.model.Workflow;
import java.util.List;

public interface WorkflowService {
    List<Workflow> findAll();
    Workflow findById(Long id);
    Workflow save(Workflow workflow);
    void deleteById(Long id);
}
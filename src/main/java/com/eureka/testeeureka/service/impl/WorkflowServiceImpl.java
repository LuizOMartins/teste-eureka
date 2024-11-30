package com.eureka.testeeureka.service.impl;


import com.eureka.testeeureka.model.Workflow;
import com.eureka.testeeureka.repository.WorkflowRepository;
import com.eureka.testeeureka.service.WorkflowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkflowServiceImpl implements WorkflowService {

    @Autowired
    private WorkflowRepository workflowRepository;

    @Override
    public List<Workflow> findAll() {
        return workflowRepository.findAll();
    }

    @Override
    public Workflow findById(Long id) {
        return workflowRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Workflow not found with id " + id));
    }

    @Override
    public Workflow save(Workflow workflow) {
        return workflowRepository.save(workflow);
    }

    @Override
    public void deleteById(Long id) {
        workflowRepository.deleteById(id);
    }
}
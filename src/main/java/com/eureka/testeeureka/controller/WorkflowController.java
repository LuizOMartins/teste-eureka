package com.eureka.testeeureka.controller;


import com.eureka.testeeureka.model.Workflow;
import com.eureka.testeeureka.service.WorkflowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workflows")
public class WorkflowController {

    @Autowired
    private WorkflowService workflowService;

    @GetMapping
    public List<Workflow> getAllWorkflows() {
        return workflowService.findAll();
    }

    @GetMapping("/{id}")
    public Workflow getWorkflowById(@PathVariable Long id) {
        return workflowService.findById(id);
    }

    @PostMapping
    public Workflow createWorkflow(@RequestBody Workflow workflow) {
        return workflowService.save(workflow);
    }

    @DeleteMapping("/{id}")
    public void deleteWorkflow(@PathVariable Long id) {
        workflowService.deleteById(id);
    }
}
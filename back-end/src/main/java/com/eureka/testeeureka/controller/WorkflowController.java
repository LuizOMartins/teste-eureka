package com.eureka.testeeureka.controller;


import com.eureka.testeeureka.dto.WorkflowDTO;
import com.eureka.testeeureka.model.Workflow;
import org.springframework.web.bind.annotation.*;
import com.eureka.testeeureka.service.WorkflowService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/workflows")
public class WorkflowController {

    @Autowired
    private WorkflowService workflowService;

    @GetMapping
    public List<WorkflowDTO> getAllWorkflows() {
        return workflowService.findAll()
                .stream()
                .map(WorkflowDTO::new)
                .collect(Collectors.toList());
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

package com.eureka.testeeureka.service.impl;


import com.eureka.testeeureka.model.Clients;
import com.eureka.testeeureka.model.Step;
import org.hibernate.Hibernate;
import com.eureka.testeeureka.model.Script;
import com.eureka.testeeureka.repository.ScriptRepository;
import com.eureka.testeeureka.service.ScriptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ScriptServiceImpl implements ScriptService {

    @Autowired
    private ScriptRepository scriptRepository;

    @Override
    public List<Script> findAll() {
        return scriptRepository.findAll();
    }

    @Override
    public Script findById(Long id) {
        return scriptRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Script not found with id " + id));
    }

    @Override
    public Script save(Script script) {
        return scriptRepository.save(script);
    }

    @Override
    public void deleteById(Long id) {
        scriptRepository.deleteById(id);
    }

    public List<Script> findByClient(Clients client) {
        return scriptRepository.findByClient(client);
    }

    public List<Script> findByClientId(Long clientId) {
        return scriptRepository.findByClientId(clientId);
    }

    @Transactional
    public List<Map<String, Object>> findScriptDetailsByClientId(Long clientId) {
        List<Script> scripts = scriptRepository.findByClientId(clientId);

        List<Map<String, Object>> scriptDetailsList = new ArrayList<>();
        for (Script script : scripts) {
            Hibernate.initialize(script.getContent());
            Hibernate.initialize(script.getWorkflow().getSteps());
            Map<String, Object> scriptDetails = new HashMap<>();
            scriptDetails.put("scriptId", script.getId());
            scriptDetails.put("content", script.getContent());
            scriptDetails.put("workflowId", script.getWorkflow().getId());
            scriptDetails.put("currentStep", script.getCurrentStep() != null ? script.getCurrentStep().getName() : null);

            List<String> workflowSteps = script.getWorkflow().getSteps().stream()
                    .map(Step::getName)
                    .collect(Collectors.toList());
            scriptDetails.put("workflowSteps", workflowSteps);

            scriptDetailsList.add(scriptDetails);
        }

        return scriptDetailsList;
    }

    @Override
    @Transactional
    public List<Map<String, Object>> findFilteredScripts(String status, LocalDate dateSent, String email) {
        boolean noFilters = (status == null || status.isEmpty()) &&
                dateSent == null &&
                (email == null || email.isEmpty());
        if (noFilters) {
            List<Script> allScripts = scriptRepository.findAll();
            return allScripts.stream().map(this::mapScriptDetails).collect(Collectors.toList());
        }

        // Caso contrário, aplique os filtros
        List<Script> filteredScripts = scriptRepository.findFilteredScripts(
                status != null && !status.isEmpty() ? status : null,
                dateSent,
                email != null && !email.isEmpty() ? email : null
        );

        return filteredScripts.stream().map(this::mapScriptDetails).collect(Collectors.toList());
    }

    private Map<String, Object> mapScriptDetails(Script script) {
        Map<String, Object> scriptDetails = new HashMap<>();
        scriptDetails.put("scriptId", script.getId());
        scriptDetails.put("content", script.getContent());
        scriptDetails.put("workflowId", script.getWorkflow().getId());
        scriptDetails.put("currentStep", script.getCurrentStep() != null ? script.getCurrentStep().getName() : null);
        scriptDetails.put("workflowSteps", script.getWorkflow().getSteps().stream()
                .map(Step::getName)
                .collect(Collectors.toList()));
        scriptDetails.put("stepReviews", script.getWorkflow().getSteps().stream()
                .flatMap(step -> step.getStepReviews().stream())
                .map(review -> Map.of(
                        "stepId", review.getStep().getId(),
                        "userId", review.getUserId(),
                        "status", review.getStatus(),
                        "comment", review.getComment()
                ))
                .collect(Collectors.toList()));
        return scriptDetails;
    }




}

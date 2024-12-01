package com.eureka.testeeureka.service.impl;


import com.eureka.testeeureka.model.Clients;
import org.hibernate.Hibernate;
import com.eureka.testeeureka.model.Script;
import com.eureka.testeeureka.repository.ScriptRepository;
import com.eureka.testeeureka.service.ScriptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            Map<String, Object> scriptDetails = new HashMap<>();
            scriptDetails.put("scriptId", script.getId());
            scriptDetails.put("content", script.getContent());
            scriptDetails.put("workflowId", script.getWorkflow().getId());
            scriptDetails.put("currentStep", script.getCurrentStep().getName());
            scriptDetailsList.add(scriptDetails);
        }

        return scriptDetailsList;
    }

}

package com.eureka.testeeureka.service.impl;

import com.eureka.testeeureka.model.*;
import com.eureka.testeeureka.repository.ScriptRepository;
import com.eureka.testeeureka.repository.StepRepository;
import com.eureka.testeeureka.repository.StepTransitionsRepository;
import com.eureka.testeeureka.repository.WorkflowRepository;
import com.eureka.testeeureka.service.WorkflowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkflowServiceImpl implements WorkflowService {

    @Autowired
    private StepRepository stepRepository;

    @Autowired
    private ScriptRepository scriptRepository;

    @Autowired
    private StepTransitionsRepository stepTransitionsRepository;

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

    public boolean canTransitionTo(Long fromStepId, Long toStepId) {
        return stepTransitionsRepository.existsByFromStepIdAndToStepId(fromStepId, toStepId);
    }

    public Step executeAction(Long scriptId, Users user) {
        Script script = scriptRepository.findById(scriptId)
                .orElseThrow(() -> new RuntimeException("Script não encontrado"));
        Step currentStep = script.getCurrentStep();
        Step nextStep = getNextStep(currentStep.getId());

        script.setCurrentStep(nextStep);
        scriptRepository.save(script);
        return nextStep;
    }

    @Override
    public Step getNextStep(Long fromStepId) {
        StepTransitions transition = stepTransitionsRepository.findByFromStepId(fromStepId)
                .orElseThrow(() -> new RuntimeException("Nenhuma transição encontrada para o Step ID: " + fromStepId));

        return transition.getToStep();
    }
}

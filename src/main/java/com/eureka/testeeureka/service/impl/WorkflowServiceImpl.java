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
import java.util.Optional;

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


    @Override
    public Step getNextStep(Long currentStepId) {
        Optional<StepTransitions> transition = stepTransitionsRepository.findByFromStepId(currentStepId);

        if (transition.isPresent()) {
            return transition.get().getToStep();
        } else {
            throw new IllegalStateException("Não há transições disponíveis para o Step atual.");
        }
    }

    public void moveToNextStep(Workflow workflow) {
        Step currentStep = workflow.getCurrentStep();
        Step nextStep = getNextStep(currentStep.getId());
        workflow.setCurrentStep(nextStep);
        workflowRepository.save(workflow);
    }
}

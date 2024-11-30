package com.eureka.testeeureka.service.impl;

import com.eureka.testeeureka.model.*;
import com.eureka.testeeureka.repository.*;
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
    private StepReviewsRepository stepReviewsRepository;

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

    @Override
    public List<Step> findAllByWorkflowId(Long workflowId) {
        return stepRepository.findAllByWorkflowId(workflowId);
    }

    @Override
    public void analysisStep(Workflow workflow, Long userId, String comment, String userRole) {
        Step currentStep = workflow.getCurrentStep();

        // Validar se o Step foi assumido por algum usuário
        Optional<StepReviews> reviewOpt = stepReviewsRepository.findByStepIdAndUserId(currentStep.getId(), userId);

        if (reviewOpt.isEmpty()) {
            throw new IllegalStateException("O usuário não assumiu a etapa atual.");
        }

        // Validar se o usuário tem a role necessária para modificar o Step
        String requiredRole = currentStep.getRoleRequired();
        if (requiredRole != null && !requiredRole.equalsIgnoreCase(userRole)) {
            throw new SecurityException("Usuário não possui a role necessária para alterar o Step atual.");
        }

        // Atualizar a revisão
        StepReviews review = reviewOpt.get();
        review.setComment(comment);
        review.setStatus("completed");
        stepReviewsRepository.save(review);

        // Validar transição para o próximo Step
        Optional<StepTransitions> transitionOpt = stepTransitionsRepository.findByFromStepId(currentStep.getId());

        if (transitionOpt.isEmpty()) {
            throw new IllegalStateException("Não há transições disponíveis para a etapa atual.");
        }

        Step nextStep = transitionOpt.get().getToStep();
        workflow.setCurrentStep(nextStep);
        workflowRepository.save(workflow); // Salvar Workflow com o novo Step
    }


}

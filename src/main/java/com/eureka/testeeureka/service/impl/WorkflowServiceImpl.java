package com.eureka.testeeureka.service.impl;

import com.eureka.testeeureka.model.*;
import com.eureka.testeeureka.repository.*;
import com.eureka.testeeureka.enums.StepType;
import org.springframework.stereotype.Service;
import com.eureka.testeeureka.service.WorkflowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class WorkflowServiceImpl implements WorkflowService {

    private static final Long STATUS_REJECTED = 8L;

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
    @Transactional(readOnly = true)
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
        List<StepTransitions> transitions = stepTransitionsRepository.findByFromStepId(currentStepId);

        if (transitions.isEmpty()) {
            throw new IllegalStateException("Não há transições disponíveis para o Step atual.");
        }
        return transitions.stream()
                .map(StepTransitions::getToStep)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Nenhum Step de destino encontrado nas transições."));
    }


    @Override
    public List<Step> findAllByWorkflowId(Long workflowId) {
        return stepRepository.findAllByWorkflowId(workflowId);
    }

    public void analysisStep(Workflow workflow, Long userId, String comment, String userRole, boolean isApproved) {
        Step currentStep = workflow.getCurrentStep();
        System.out.println("Step atual recebido: " + currentStep);

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

        // Atualizar revisão
        StepReviews review = reviewOpt.get();
        review.setComment(comment);
        review.setStatus(isApproved ? "approved" : "rejected");
        stepReviewsRepository.save(review);

        // Buscar a transição baseada na decisão
        Long nextStepId = isApproved ? StepType.EM_ANALISE.getId() : StepType.RECUSADO.getId();
        System.out.println("Buscando transição de: " + currentStep.getId() + " para: " + nextStepId);

        Optional<StepTransitions> transitionOpt = stepTransitionsRepository.findByFromStepIdAndToStepId(
                currentStep.getId(), nextStepId);

        if (transitionOpt.isEmpty()) {
            throw new IllegalStateException("Não há transições disponíveis para a etapa atual.");
        }

        // Atualizar o Workflow para o próximo Step
        Step nextStep = transitionOpt.get().getToStep();
        workflow.setCurrentStep(nextStep);
        workflowRepository.save(workflow);
    }
}

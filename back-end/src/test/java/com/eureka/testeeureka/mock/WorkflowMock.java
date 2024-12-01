package com.eureka.testeeureka.mock;

import java.util.List;
import java.util.Date;
import java.util.ArrayList;
import com.eureka.testeeureka.model.*;

public class WorkflowMock {

    public static Workflow createMockWorkflow() {
        Workflow workflow = new Workflow();
        workflow.setId(1L);
        workflow.setName("COOPERFILME_Workflow");
        workflow.setDescription("Workflow para aprovação de roteiros da COOPERFILME");

        return workflow;
    }

    public static List<Step> createMockSteps(Workflow workflow) {
        List<Step> steps = new ArrayList<>();

        steps.add(createStep(StepType.AGUARDANDO_ANALISE, workflow, "Cliente envia o roteiro. Aguardando usuário assumir.", null));
        steps.add(createStep(StepType.EM_ANALISE, workflow, "Analista revisa o roteiro.", "ANALISTA"));
        steps.add(createStep(StepType.AGUARDANDO_REVISAO, workflow, "Aguardando Revisor assumir.", "REVISOR"));
        steps.add(createStep(StepType.EM_REVISAO, workflow, "Revisor aponta erros ou ideias.", "REVISOR"));
        steps.add(createStep(StepType.AGUARDANDO_APROVACAO, workflow, "Aguardando votos de aprovadores.", "APROVADOR"));
        steps.add(createStep(StepType.EM_APROVACAO, workflow, "Em votação para aprovação final.", "APROVADOR"));
        steps.add(createStep(StepType.APROVADO, workflow, "Roteiro aprovado e finalizado.", null));
        steps.add(createStep(StepType.RECUSADO, workflow, "Roteiro recusado. Finalizado.", null));

        return steps;
    }

    public static List<StepTransitions> createMockTransitions(Workflow workflow, List<Step> steps) {
        List<StepTransitions> transitions = new ArrayList<>();

        transitions.add(createTransition(StepType.AGUARDANDO_ANALISE, StepType.EM_ANALISE, workflow, steps));

        transitions.add(createTransition(StepType.EM_ANALISE, StepType.AGUARDANDO_REVISAO, workflow, steps));
        transitions.add(createTransition(StepType.EM_ANALISE, StepType.RECUSADO, workflow, steps));

        transitions.add(createTransition(StepType.AGUARDANDO_REVISAO, StepType.EM_REVISAO, workflow, steps));
        transitions.add(createTransition(StepType.EM_REVISAO, StepType.AGUARDANDO_APROVACAO, workflow, steps));

        transitions.add(createTransition(StepType.AGUARDANDO_APROVACAO, StepType.EM_APROVACAO, workflow, steps));
        transitions.add(createTransition(StepType.EM_APROVACAO, StepType.APROVADO, workflow, steps));
        transitions.add(createTransition(StepType.EM_APROVACAO, StepType.RECUSADO, workflow, steps));

        return transitions;
    }

    private static Step createStep(StepType stepType, Workflow workflow, String description, String roleRequired) {
        Step step = new Step();
        step.setId(stepType.getId());
        step.setWorkflow(workflow);
        step.setName(stepType.name().toLowerCase());
        step.setDescription(description);
        step.setRoleRequired(roleRequired);
        step.setCreatedAt(new Date());

        return step;
    }

    private static StepTransitions createTransition(StepType from, StepType to, Workflow workflow, List<Step> steps) {
        StepTransitions transition = new StepTransitions();
        transition.setId(from.getId());
        transition.setWorkflow(workflow);
        transition.setFromStep(findStepByType(steps, from));
        transition.setToStep(findStepByType(steps, to));

        return transition;
    }

    private static Step findStepByType(List<Step> steps, StepType stepType) {
        return steps.stream()
                .filter(step -> step.getId().equals(stepType.getId()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Step não encontrado para o tipo: " + stepType));
    }

    public static List<StepReviews> createMockStepReviews(List<Step> steps, Long userId) {
        List<StepReviews> reviews = new ArrayList<>();
        for (Step step : steps) {
            StepReviews review = new StepReviews();
            review.setId(step.getId());
            review.setStep(step);
            review.setUserId(userId);
            review.setStatus("pending");
            reviews.add(review);
        }

        return reviews;
    }
}

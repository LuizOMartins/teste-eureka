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

        steps.add(createStep(1L, workflow, "aguardando_analise", "Cliente envia o roteiro. Aguardando usuário assumir.", null));
        steps.add(createStep(2L, workflow, "em_analise", "Analista revisa o roteiro.", "ANALISTA"));
        steps.add(createStep(3L, workflow, "aguardando_revisao", "Aguardando Revisor assumir.", "REVISOR"));
        steps.add(createStep(4L, workflow, "em_revisao", "Revisor aponta erros ou ideias.", "REVISOR"));
        steps.add(createStep(5L, workflow, "aguardando_aprovacao", "Aguardando votos de aprovadores.", "APROVADOR"));
        steps.add(createStep(6L, workflow, "em_aprovacao", "Em votação para aprovação final.", "APROVADOR"));
        steps.add(createStep(7L, workflow, "aprovado", "Roteiro aprovado e finalizado.", null));
        steps.add(createStep(8L, workflow, "recusado", "Roteiro recusado. Finalizado.", null));

        return steps;
    }

    public static List<StepTransitions> createMockTransitions(Workflow workflow, List<Step> steps) {
        List<StepTransitions> transitions = new ArrayList<>();

        transitions.add(createTransition(1L, workflow, steps.get(0), steps.get(1))); // De aguardando_analise para em_analise
        transitions.add(createTransition(2L, workflow, steps.get(1), steps.get(2))); // De em_analise para aguardando_revisao
        transitions.add(createTransition(3L, workflow, steps.get(1), steps.get(7))); // De em_analise para recusado
        transitions.add(createTransition(4L, workflow, steps.get(2), steps.get(3))); // De aguardando_revisao para em_revisao
        transitions.add(createTransition(5L, workflow, steps.get(3), steps.get(4))); // De em_revisao para aguardando_aprovacao
        transitions.add(createTransition(6L, workflow, steps.get(4), steps.get(5))); // De aguardando_aprovacao para em_aprovacao
        transitions.add(createTransition(7L, workflow, steps.get(5), steps.get(6))); // De em_aprovacao para aprovado
        transitions.add(createTransition(8L, workflow, steps.get(5), steps.get(7))); // De em_aprovacao para recusado

        return transitions;
    }

    private static Step createStep(Long id, Workflow workflow, String name, String description, String roleRequired) {
        Step step = new Step();
        step.setId(id);
        step.setWorkflow(workflow);
        step.setName(name);
        step.setDescription(description);
        step.setRoleRequired(roleRequired);
        step.setCreatedAt(new Date());

        return step;
    }

    private static StepTransitions createTransition(Long id, Workflow workflow, Step fromStep, Step toStep) {
        StepTransitions transition = new StepTransitions();
        transition.setId(id);
        transition.setWorkflow(workflow);
        transition.setFromStep(fromStep);
        transition.setToStep(toStep);

        return transition;
    }
}

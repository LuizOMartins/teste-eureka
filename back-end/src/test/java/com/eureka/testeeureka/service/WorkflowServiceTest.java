package com.eureka.testeeureka.service;
import com.eureka.testeeureka.mock.StepType;
import com.eureka.testeeureka.mock.WorkflowMock;
import com.eureka.testeeureka.model.Step;
import com.eureka.testeeureka.model.StepTransitions;
import com.eureka.testeeureka.model.Workflow;
import com.eureka.testeeureka.repository.StepRepository;
import com.eureka.testeeureka.repository.StepReviewsRepository;
import com.eureka.testeeureka.repository.StepTransitionsRepository;
import com.eureka.testeeureka.repository.WorkflowRepository;
import com.eureka.testeeureka.service.impl.WorkflowServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;
import com.eureka.testeeureka.model.StepReviews;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WorkflowServiceTest {

    @InjectMocks
    private WorkflowServiceImpl workflowService;

    @Mock
    private StepRepository stepRepository;

    @Mock
    private StepTransitionsRepository stepTransitionsRepository;

    @Mock
    private WorkflowRepository workflowRepository;

    @Mock
    private StepReviewsRepository stepReviewsRepository;

    private Workflow mockWorkflow;
    private List<Step> mockSteps;
    private List<StepTransitions> mockTransitions;

    @BeforeEach
    void setUp() {
        mockWorkflow = WorkflowMock.createMockWorkflow();
        mockSteps = WorkflowMock.createMockSteps(mockWorkflow);
        mockTransitions = WorkflowMock.createMockTransitions(mockWorkflow, mockSteps);
    }
    @Test
    void testTransitionFromAguardandoAnaliseToEmAnalise() {
        Step currentStep = mockSteps.stream()
                .filter(step -> step.getId().equals(StepType.AGUARDANDO_ANALISE.getId()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Current step não encontrado: " + StepType.AGUARDANDO_ANALISE));
        StepTransitions transition = mockTransitions.stream()
                .filter(t -> t.getFromStep().getId().equals(StepType.AGUARDANDO_ANALISE.getId()) &&
                        t.getToStep().getId().equals(StepType.EM_ANALISE.getId()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Transition não encontrada: AGUARDANDO_ANALISE -> EM_ANALISE"));

        Long userId = 123L;
        String userRole = null;
        boolean isApproved = true;

        // Mock para StepReviews
        StepReviews review = new StepReviews();
        review.setId(1L);
        review.setStep(currentStep);
        review.setUserId(userId);
        review.setStatus("pending");

        mockWorkflow.setCurrentStep(currentStep);

        // Configuração dos mocks
        lenient().when(stepReviewsRepository.findByStepIdAndUserId(currentStep.getId(), userId))
                .thenReturn(Optional.of(review));
        lenient().when(stepTransitionsRepository.findByFromStepIdAndToStepId(
                        StepType.AGUARDANDO_ANALISE.getId(), StepType.EM_ANALISE.getId()))
                .thenReturn(Optional.of(transition)); // Transição para em_analise
        when(workflowRepository.save(mockWorkflow)).thenReturn(mockWorkflow);

        System.out.println("Step atual: " + currentStep);
        System.out.println("Transição mockada: " + transition);

        // Transição para o próximo Step
        try {
            workflowService.analysisStep(mockWorkflow, userId, "Assumindo o roteiro", userRole, isApproved);
        } catch (IllegalStateException e) {
            System.out.println("Erro capturado: " + e.getMessage());
            throw e;
        }

        // Validação
        Step resultStep = mockWorkflow.getCurrentStep();
        assertNotNull(resultStep);
        assertEquals(StepType.EM_ANALISE.name().toLowerCase(), resultStep.getName());

        // Verificar que o Workflow foi salvo com o novo Step
        verify(workflowRepository, times(1)).save(mockWorkflow);
        verify(stepReviewsRepository, times(1)).save(review);
    }


}

package com.eureka.testeeureka.service;

import com.eureka.testeeureka.mock.WorkflowMock;
import com.eureka.testeeureka.model.Step;
import com.eureka.testeeureka.model.StepReviews;
import com.eureka.testeeureka.model.StepTransitions;
import com.eureka.testeeureka.model.Workflow;
import com.eureka.testeeureka.repository.StepRepository;
import com.eureka.testeeureka.repository.StepReviewsRepository;
import com.eureka.testeeureka.repository.StepTransitionsRepository;
import com.eureka.testeeureka.repository.WorkflowRepository;
import com.eureka.testeeureka.service.impl.WorkflowServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;

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
        // Configuração inicial
        Step currentStep = mockSteps.get(0); // Step aguardando_analise
        StepTransitions transition = mockTransitions.get(0); // Transição aguardando_analise -> em_analise
        Long userId = 123L;
        String userRole = null; // Step inicial não requer role específica

        // Mock para StepReviews
        StepReviews review = new StepReviews();
        review.setId(1L);
        review.setStep(currentStep);
        review.setUserId(userId);
        review.setStatus("pending");

        mockWorkflow.setCurrentStep(currentStep); // Define o Step inicial

        // Configuração dos mocks
        lenient().when(stepTransitionsRepository.findByFromStepId(currentStep.getId())).thenReturn(Optional.of(transition));
        lenient().when(stepReviewsRepository.findByStepIdAndUserId(currentStep.getId(), userId)).thenReturn(Optional.of(review));
        when(workflowRepository.save(mockWorkflow)).thenReturn(mockWorkflow);

        // Transição para o próximo Step
        workflowService.analysisStep(mockWorkflow, userId, "Assumindo o roteiro", userRole);

        // Validação
        Step resultStep = mockWorkflow.getCurrentStep();
        assertNotNull(resultStep);
        assertEquals("em_analise", resultStep.getName());

        // Verificar que o Workflow foi salvo com o novo Step
        verify(workflowRepository, times(1)).save(mockWorkflow);
        verify(stepReviewsRepository, times(1)).save(review);
    }


}

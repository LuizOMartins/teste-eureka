package com.eureka.testeeureka.service;

import com.eureka.testeeureka.mock.WorkflowMock;
import com.eureka.testeeureka.model.Step;
import com.eureka.testeeureka.model.StepTransitions;
import com.eureka.testeeureka.model.Workflow;
import com.eureka.testeeureka.repository.StepRepository;
import com.eureka.testeeureka.repository.StepTransitionsRepository;
import com.eureka.testeeureka.service.impl.WorkflowServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

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
        Step currentStep = mockSteps.get(0);
        StepTransitions transition = mockTransitions.get(0);

        lenient().when(stepRepository.findById(currentStep.getId())).thenReturn(java.util.Optional.of(currentStep));
        lenient().when(stepTransitionsRepository.findByFromStepId(currentStep.getId())).thenReturn(java.util.Optional.of(transition));

        Step resultStep = workflowService.getNextStep(currentStep.getId());

        assertNotNull(resultStep);
        assertEquals("em_analise", resultStep.getName());
    }
}

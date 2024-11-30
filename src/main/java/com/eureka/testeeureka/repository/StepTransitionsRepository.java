package com.eureka.testeeureka.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import com.eureka.testeeureka.model.StepTransitions;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface StepTransitionsRepository extends JpaRepository<StepTransitions, Long> {

    boolean existsByFromStepIdAndToStepId(Long fromStepId, Long toStepId);
    Optional<StepTransitions> findByFromStepIdAndToStepId(Long fromSteId, Long toStepId);
    List<StepTransitions> findByFromStepId(Long fromStepId);
}

package com.eureka.testeeureka.repository;

import java.util.Optional;
import org.springframework.stereotype.Repository;
import com.eureka.testeeureka.model.StepTransitions;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface StepTransitionsRepository extends JpaRepository<StepTransitions, Long> {

    Optional<StepTransitions> findByFromStepId(Long fromStepId);
    boolean existsByFromStepIdAndToStepId(Long fromStepId, Long toStepId);
}

package com.eureka.testeeureka.repository;

import java.util.List;
import com.eureka.testeeureka.model.Step;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface StepRepository extends JpaRepository<Step, Long> {
    List<Step> findAllByWorkflowId(Long workflowId);

}

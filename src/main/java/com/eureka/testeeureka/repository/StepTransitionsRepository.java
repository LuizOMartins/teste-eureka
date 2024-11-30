package com.eureka.testeeureka.repository;


import com.eureka.testeeureka.model.StepTransitions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StepTransitionsRepository extends JpaRepository<StepTransitions, Long> {
}
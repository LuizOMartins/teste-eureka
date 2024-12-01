package com.eureka.testeeureka.service;

import com.eureka.testeeureka.model.Step;
import java.util.List;

public interface StepService {
    List<Step> findAll();
    Step findById(Long id);
    Step save(Step step);
    void deleteById(Long id);
}
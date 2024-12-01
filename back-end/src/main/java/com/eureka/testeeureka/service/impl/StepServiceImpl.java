package com.eureka.testeeureka.service.impl;


import com.eureka.testeeureka.model.Step;
import com.eureka.testeeureka.repository.StepRepository;
import com.eureka.testeeureka.service.StepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StepServiceImpl implements StepService {

    @Autowired
    private StepRepository stepRepository;

    @Override
    public List<Step> findAll() {
        return stepRepository.findAll();
    }

    @Override
    public Step findById(Long id) {
        return stepRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Step not found with id " + id));
    }

    @Override
    public Step save(Step step) {
        return stepRepository.save(step);
    }

    @Override
    public void deleteById(Long id) {
        stepRepository.deleteById(id);
    }
}
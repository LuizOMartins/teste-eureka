package com.eureka.testeeureka.service.impl;


import com.eureka.testeeureka.model.Script;
import com.eureka.testeeureka.repository.ScriptRepository;
import com.eureka.testeeureka.service.ScriptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScriptServiceImpl implements ScriptService {

    @Autowired
    private ScriptRepository scriptRepository;

    @Override
    public List<Script> findAll() {
        return scriptRepository.findAll();
    }

    @Override
    public Script findById(Long id) {
        return scriptRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Script not found with id " + id));
    }

    @Override
    public Script save(Script script) {
        return scriptRepository.save(script);
    }

    @Override
    public void deleteById(Long id) {
        scriptRepository.deleteById(id);
    }
}
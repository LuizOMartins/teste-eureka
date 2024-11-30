package com.eureka.testeeureka.service;

import com.eureka.testeeureka.model.Script;
import java.util.List;

public interface ScriptService {
    List<Script> findAll();
    Script findById(Long id);
    Script save(Script script);
    void deleteById(Long id);
}

package com.eureka.testeeureka.service;

import com.eureka.testeeureka.model.Clients;
import com.eureka.testeeureka.model.Script;
import java.util.List;
import java.util.Map;

public interface ScriptService {
    List<Script> findAll();
    Script findById(Long id);
    Script save(Script script);
    void deleteById(Long id);
    List<Script> findByClient(Clients client);
    List<Script> findByClientId(Long clientId);
    List<Map<String, Object>> findScriptDetailsByClientId(Long clientId);
}

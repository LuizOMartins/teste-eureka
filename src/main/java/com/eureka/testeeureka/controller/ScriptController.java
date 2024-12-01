package com.eureka.testeeureka.controller;


import com.eureka.testeeureka.dto.ScriptDTO;
import com.eureka.testeeureka.model.Clients;
import com.eureka.testeeureka.model.Script;
import com.eureka.testeeureka.model.Workflow;
import com.eureka.testeeureka.service.ScriptService;
import com.eureka.testeeureka.service.WorkflowService;
import com.eureka.testeeureka.service.impl.ClientsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/scripts")
public class ScriptController {

    private final ScriptService scriptService;
    private final WorkflowService workflowService;
    private final ClientsService clientsService;

    public ScriptController(ScriptService scriptService, WorkflowService workflowService, ClientsService clientsService) {
        this.scriptService = scriptService;
        this.workflowService = workflowService;
        this.clientsService = clientsService;
    }

    @PostMapping
    public ResponseEntity<String> createScript(@RequestBody ScriptDTO scriptDTO) {
        try {
            Workflow workflow = workflowService.findById(scriptDTO.getWorkflowId());
            if (workflow == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Workflow não encontrado.");
            }


            Clients client = null;
            if (scriptDTO.getClientId() != null) {
                client = clientsService.findById(scriptDTO.getClientId());
                if (client == null) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado.");
                }
            }

            Script script = new Script();
            script.setWorkflow(workflow);
            script.setClient(client);
            script.setContent(scriptDTO.getContent());
            script.setCreatedAt(new java.util.Date());
            script.setUpdatedAt(new java.util.Date());

            scriptService.save(script);

            return ResponseEntity.status(HttpStatus.CREATED).body("Script criado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao criar o Script: " + e.getMessage());
        }
    }
}

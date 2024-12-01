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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/api/scripts")
@Tag(name = "Scripts", description = "API para gerenciamento de scripts")
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
    @Operation(summary = "Criar um novo script com cliente",
            description = "Endpoint para criar um novo script associado a um workflow e cadastrar um cliente.")
    public ResponseEntity<String> createScriptWithClient(@RequestBody ScriptDTO scriptDTO) {
        try {
            // Busca o workflow pelo ID
            Workflow workflow = workflowService.findById(scriptDTO.getWorkflowId());
            if (workflow == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Workflow não encontrado.");
            }

            // Cria o cliente
            Clients client = new Clients();
            client.setName(scriptDTO.getClientName());
            client.setEmail(scriptDTO.getClientEmail());
            client.setPhone(scriptDTO.getClientPhone());
            client = clientsService.save(client);

            // Cria o script
            Script script = new Script();
            script.setWorkflow(workflow);
            script.setClient(client);
            script.setContent(scriptDTO.getContent());
            scriptService.save(script);

            return ResponseEntity.status(HttpStatus.CREATED).body("Script e cliente criados com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao criar script e cliente: " + e.getMessage());
        }
    }
}

package com.eureka.testeeureka.controller;


import com.eureka.testeeureka.dto.ClientDTO;
import com.eureka.testeeureka.dto.ScriptDTO;
import com.eureka.testeeureka.model.Clients;
import com.eureka.testeeureka.model.Script;
import com.eureka.testeeureka.model.Step;
import com.eureka.testeeureka.model.Workflow;
import com.eureka.testeeureka.service.ScriptService;
import com.eureka.testeeureka.service.WorkflowService;
import com.eureka.testeeureka.service.impl.ClientsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


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
    @Operation(summary = "Criar um novo script", description = "Endpoint para criar um novo script associado a um workflow e, opcionalmente, a um cliente.")
    public ResponseEntity<String> createScript(@RequestBody ScriptDTO scriptDTO) {
        try {
            Workflow workflow = workflowService.findById(scriptDTO.getWorkflowId());
            if (workflow == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Workflow não encontrado.");
            }

            Clients client = null;
            if (scriptDTO.getClient() != null) {
                ClientDTO clientDTO = scriptDTO.getClient();
                client = new Clients();
                client.setName(clientDTO.getName());
                client.setEmail(clientDTO.getEmail());
                client.setPhone(clientDTO.getPhone());
                client = clientsService.save(client);
            }

            Step firstStep = workflow.getSteps().stream()
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("O Workflow não possui steps configurados."));

            Script script = new Script();
            script.setWorkflow(workflow);
            script.setClient(client);
            script.setContent(scriptDTO.getContent());
            script.setCurrentStep(firstStep);
            scriptService.save(script);

            return ResponseEntity.status(HttpStatus.CREATED).body("Script criado com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao criar script: " + e.getMessage());
        }
    }

    @GetMapping("/by-client")
    @Operation(
            summary = "Consultar Script por Cliente",
            description = "Consulta os scripts associados a um cliente com base no e-mail ou telefone. Retorna o step atual do script e as etapas do workflow associado."
    )
    public ResponseEntity<?> getScriptsByClient(
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "phone", required = false) String phone
    ) {
        try {
            if (email == null && phone == null) {
                return ResponseEntity.badRequest().body("Por favor, forneça email ou phone para a consulta.");
            }

            Clients client = clientsService.findByEmailOrPhone(email, phone);
            if (client == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado.");
            }

            List<Map<String, Object>> scripts = scriptService.findScriptDetailsByClientId(client.getId());
            if (scripts.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum script encontrado para o cliente fornecido.");
            }

            return ResponseEntity.ok(scripts);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao consultar scripts: " + e.getMessage());
        }
    }


}

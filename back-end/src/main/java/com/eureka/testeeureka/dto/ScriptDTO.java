package com.eureka.testeeureka.dto;

import jakarta.validation.constraints.NotNull;

public class ScriptDTO {

    @NotNull(message = "O ID do workflow é obrigatório.")
    private Long workflowId;

    @NotNull(message = "O conteúdo do script é obrigatório.")
    private String content;

    @NotNull(message = "As informações do cliente são obrigatórias.")
    private ClientDTO client;

    public Long getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(Long workflowId) {
        this.workflowId = workflowId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ClientDTO getClient() {
        return client;
    }

    public void setClient(ClientDTO client) {
        this.client = client;
    }
}

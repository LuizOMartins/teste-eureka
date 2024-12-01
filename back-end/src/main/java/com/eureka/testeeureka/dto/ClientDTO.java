package com.eureka.testeeureka.dto;

import jakarta.validation.constraints.NotNull;

public class ClientDTO {
    @NotNull(message = "O nome do cliente é obrigatório.")
    private String name;

    public @NotNull(message = "O nome do cliente é obrigatório.") String getName() {
        return name;
    }

    public void setName(@NotNull(message = "O nome do cliente é obrigatório.") String name) {
        this.name = name;
    }

    public @NotNull(message = "O email do cliente é obrigatório.") String getEmail() {
        return email;
    }

    public void setEmail(@NotNull(message = "O email do cliente é obrigatório.") String email) {
        this.email = email;
    }

    public @NotNull(message = "O telefone do cliente é obrigatório.") String getPhone() {
        return phone;
    }

    public void setPhone(@NotNull(message = "O telefone do cliente é obrigatório.") String phone) {
        this.phone = phone;
    }

    @NotNull(message = "O email do cliente é obrigatório.")
    private String email;

    @NotNull(message = "O telefone do cliente é obrigatório.")
    private String phone;
}

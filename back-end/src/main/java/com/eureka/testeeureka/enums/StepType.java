package com.eureka.testeeureka.enums;

public enum StepType {
    AGUARDANDO_ANALISE(1L),
    EM_ANALISE(2L),
    AGUARDANDO_REVISAO(3L),
    EM_REVISAO(4L),
    AGUARDANDO_APROVACAO(5L),
    EM_APROVACAO(6L),
    APROVADO(7L),
    RECUSADO(8L);

    private final Long id;

    StepType(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}

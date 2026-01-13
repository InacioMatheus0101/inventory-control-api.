package com.matheuss.controle_estoque_api.domain.history;

public enum HistoryEventType {
    CRIACAO("Criação"),
    ALOCACAO("Alocação"),
    DEVOLUCAO("Devolução"),
    MANUTENCAO_INICIO("Início da Manutenção"),
    MANUTENCAO_FIM("Fim da Manutenção"),
    DESCARTE("Descarte");

    private final String description;

    HistoryEventType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

package org.example.casodeuso1.dto;

import java.util.Set;

public class LaboratorioResponseDTO {
    private Long id;
    private String nome;
    private Set<FiltroLinhaResumeDTO> filtroLinhas;
    private PredioResumeDTO predio;

    public LaboratorioResponseDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Set<FiltroLinhaResumeDTO> getFiltroLinhas() {
        return filtroLinhas;
    }

    public void setFiltroLinhas(Set<FiltroLinhaResumeDTO> filtroLinhas) {
        this.filtroLinhas = filtroLinhas;
    }

    public PredioResumeDTO getPredio() {
        return predio;
    }

    public void setPredio(PredioResumeDTO predio) {
        this.predio = predio;
    }
}

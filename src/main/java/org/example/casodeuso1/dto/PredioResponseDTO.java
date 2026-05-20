package org.example.casodeuso1.dto;

import java.util.Set;

public class PredioResponseDTO {
    private Long id;
    private String nome;
    private String localizacao;
    private Set<LaboratorioResumeDTO> laboratorios;
    private Set<SnifferResumeDTO> sniffers;

    public PredioResponseDTO() {
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

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public Set<LaboratorioResumeDTO> getLaboratorios() {
        return laboratorios;
    }

    public void setLaboratorios(Set<LaboratorioResumeDTO> laboratorios) {
        this.laboratorios = laboratorios;
    }

    public Set<SnifferResumeDTO> getSniffers() {
        return sniffers;
    }

    public void setSniffers(Set<SnifferResumeDTO> sniffers) {
        this.sniffers = sniffers;
    }
}

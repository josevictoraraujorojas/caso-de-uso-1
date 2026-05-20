package org.example.casodeuso1.dto;

import java.util.Set;

public class FiltroLinhaResponseDTO {
    private Long id;
    private String nome;
    private int correnteMaxima;
    private int capacidade;
    private Set<AparelhoResponseDTO> aparelhos;
    private MedidorEnergiaResumeDTO medidorEnergia;

    public FiltroLinhaResponseDTO() {
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

    public int getCorrenteMaxima() {
        return correnteMaxima;
    }

    public void setCorrenteMaxima(int correnteMaxima) {
        this.correnteMaxima = correnteMaxima;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    public Set<AparelhoResponseDTO> getAparelhos() {
        return aparelhos;
    }

    public void setAparelhos(Set<AparelhoResponseDTO> aparelhos) {
        this.aparelhos = aparelhos;
    }

    public MedidorEnergiaResumeDTO getMedidorEnergia() {
        return medidorEnergia;
    }

    public void setMedidorEnergia(MedidorEnergiaResumeDTO medidorEnergia) {
        this.medidorEnergia = medidorEnergia;
    }
}

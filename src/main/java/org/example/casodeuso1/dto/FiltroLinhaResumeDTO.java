package org.example.casodeuso1.dto;

public class FiltroLinhaResumeDTO {
    private Long id;
    private String nome;
    private int correnteMaxima;
    private int capacidade;

    public FiltroLinhaResumeDTO() {
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
}

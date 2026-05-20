package org.example.casodeuso1.dto;

public class FiltroLinhaCreateDTO {
    private String nome;
    private int correnteMaxima;
    private int capacidade;

    public FiltroLinhaCreateDTO() {
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

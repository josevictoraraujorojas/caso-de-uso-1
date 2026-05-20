package org.example.casodeuso1.dto;

public class AparelhoCreateDTO {
    private String nome;
    private int potenciaNominal;
    private double corrente;

    public AparelhoCreateDTO() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getPotenciaNominal() {
        return potenciaNominal;
    }

    public void setPotenciaNominal(int potenciaNominal) {
        this.potenciaNominal = potenciaNominal;
    }

    public double getCorrente() {
        return corrente;
    }

    public void setCorrente(double corrente) {
        this.corrente = corrente;
    }
}

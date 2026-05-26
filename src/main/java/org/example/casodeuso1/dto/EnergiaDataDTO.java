package org.example.casodeuso1.dto;

public class EnergiaDataDTO {

    private double tensao;
    private double corrente;
    private double potencia;
    private double energiaKwh;
    private double frequencia;
    private double fatorPotencia;
    private String esp32Mac;


    public EnergiaDataDTO() {
    }

    public EnergiaDataDTO(String eps32Mac) {
        this.esp32Mac = eps32Mac;
    }

    public EnergiaDataDTO(double tensao,
                          double corrente,
                          double potencia,
                          double energiaKwh,
                          double frequencia,
                          double fatorPotencia, String eps32Mac) {
        this.tensao = tensao;
        this.corrente = corrente;
        this.potencia = potencia;
        this.energiaKwh = energiaKwh;
        this.frequencia = frequencia;
        this.fatorPotencia = fatorPotencia;
        this.esp32Mac = eps32Mac;
    }

    public double getTensao() {
        return tensao;
    }

    public void setTensao(double tensao) {
        this.tensao = tensao;
    }

    public double getCorrente() {
        return corrente;
    }

    public void setCorrente(double corrente) {
        this.corrente = corrente;
    }

    public double getPotencia() {
        return potencia;
    }

    public void setPotencia(double potencia) {
        this.potencia = potencia;
    }

    public double getEnergiaKwh() {
        return energiaKwh;
    }

    public void setEnergiaKwh(double energiaKwh) {
        this.energiaKwh = energiaKwh;
    }

    public double getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(double frequencia) {
        this.frequencia = frequencia;
    }

    public double getFatorPotencia() {
        return fatorPotencia;
    }

    public void setFatorPotencia(double fatorPotencia) {
        this.fatorPotencia = fatorPotencia;
    }

    public String getEsp32Mac() {
        return esp32Mac;
    }

    public void setEsp32Mac(String esp32Mac) {
        this.esp32Mac = esp32Mac;
    }
}

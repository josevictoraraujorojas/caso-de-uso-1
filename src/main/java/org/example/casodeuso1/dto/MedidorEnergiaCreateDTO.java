package org.example.casodeuso1.dto;

public class MedidorEnergiaCreateDTO {
    private String modelo;
    private int correnteNominal;

    public MedidorEnergiaCreateDTO(String modelo) {
        this.modelo = modelo;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getCorrenteNominal() {
        return correnteNominal;
    }

    public void setCorrenteNominal(int correnteNominal) {
        this.correnteNominal = correnteNominal;
    }
}

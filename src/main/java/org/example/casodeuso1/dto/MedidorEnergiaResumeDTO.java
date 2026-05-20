package org.example.casodeuso1.dto;

public class MedidorEnergiaResumeDTO {
    private Long id;
    private String modelo;
    private int correnteNominal;

    public MedidorEnergiaResumeDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

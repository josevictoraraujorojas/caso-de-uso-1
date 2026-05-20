package org.example.casodeuso1.dto;

import java.util.Set;

public class MedidorEnergiaResponseDTO {

    private Long id;
    private String modelo;
    private int correnteNominal;
    private MonitorEnergiaResponseDTO monitorEnergia;
    private Set<AparelhoResponseDTO> aparelhos;

    public MedidorEnergiaResponseDTO() {
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

    public MonitorEnergiaResponseDTO getMonitorEnergia() {
        return monitorEnergia;
    }

    public void setMonitorEnergia(MonitorEnergiaResponseDTO monitorEnergia) {
        this.monitorEnergia = monitorEnergia;
    }

    public Set<AparelhoResponseDTO> getAparelhos() {
        return aparelhos;
    }

    public void setAparelhos(Set<AparelhoResponseDTO> aparelhos) {
        this.aparelhos = aparelhos;
    }
}

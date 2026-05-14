package org.example.casodeuso1.model;

import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;

import java.time.Instant;


@Measurement(name = "energia")
public class EnergiaData {

    @Column(timestamp = true)
    private Instant time;

    @Column(name = "corrente")
    private Double corrente;

    @Column(name = "energia_kwh")
    private Double energiaKwh;

    @Column(name = "fator_potencia")
    private Double fatorPotencia;

    @Column(name = "potencia")
    private Double potencia;

    @Column(name = "tensao")
    private Double tensao;

    // TAGS

    @Column(tag = true, name = "aparelho_id")
    private String aparelhoId;

    @Column(tag = true, name = "esp_id")
    private String espId;

    @Column(tag = true, name = "filtro_id")
    private String filtroId;

    @Column(tag = true, name = "lab_id")
    private String labId;

    @Column(tag = true, name = "predio_id")
    private String predioId;

    public EnergiaData() {
    }

    public Instant getTime() {
        return time;
    }

    public void setTime(Instant time) {
        this.time = time;
    }

    public Double getCorrente() {
        return corrente;
    }

    public void setCorrente(Double corrente) {
        this.corrente = corrente;
    }

    public Double getEnergiaKwh() {
        return energiaKwh;
    }

    public void setEnergiaKwh(Double energiaKwh) {
        this.energiaKwh = energiaKwh;
    }

    public Double getFatorPotencia() {
        return fatorPotencia;
    }

    public void setFatorPotencia(Double fatorPotencia) {
        this.fatorPotencia = fatorPotencia;
    }

    public Double getPotencia() {
        return potencia;
    }

    public void setPotencia(Double potencia) {
        this.potencia = potencia;
    }

    public Double getTensao() {
        return tensao;
    }

    public void setTensao(Double tensao) {
        this.tensao = tensao;
    }

    public String getAparelhoId() {
        return aparelhoId;
    }

    public void setAparelhoId(String aparelhoId) {
        this.aparelhoId = aparelhoId;
    }

    public String getEspId() {
        return espId;
    }

    public void setEspId(String espId) {
        this.espId = espId;
    }

    public String getFiltroId() {
        return filtroId;
    }

    public void setFiltroId(String filtroId) {
        this.filtroId = filtroId;
    }

    public String getLabId() {
        return labId;
    }

    public void setLabId(String labId) {
        this.labId = labId;
    }

    public String getPredioId() {
        return predioId;
    }

    public void setPredioId(String predioId) {
        this.predioId = predioId;
    }
}

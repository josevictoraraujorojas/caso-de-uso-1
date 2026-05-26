package org.example.casodeuso1.model;

import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;

import java.time.Instant;


@Measurement(name = "energia")
public class EnergiaData {

    @Column(timestamp = true)
    private Instant time;

    @Column(name = "tensao")
    private Double tensao;

    @Column(name = "corrente")
    private Double corrente;

    @Column(name = "potencia")
    private Double potencia;

    @Column(name = "energia_kwh")
    private Double energiaKwh;

    @Column(name = "frequencia")
    private Double frequencia;

    @Column(name = "fator_potencia")
    private Double fatorPotencia;

    // TAGS
    @Column(tag = true, name = "esp_id")
    private Long espId;

    @Column(tag = true, name = "medidor_energia_id")
    private Long medidorEnergiaId;

    @Column(tag = true, name = "filtro_id")
    private Long filtroId;

    @Column(tag = true, name = "lab_id")
    private Long labId;

    @Column(tag = true, name = "predio_id")
    private Long predioId;

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


    public Long getEspId() {
        return espId;
    }

    public void setEspId(Long espId) {
        this.espId = espId;
    }

    public Double getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(Double frequencia) {
        this.frequencia = frequencia;
    }

    public Long getMedidorEnergiaId() {
        return medidorEnergiaId;
    }

    public void setMedidorEnergiaId(Long medidorEnergiaId) {
        this.medidorEnergiaId = medidorEnergiaId;
    }

    public Long getFiltroId() {
        return filtroId;
    }

    public void setFiltroId(Long filtroId) {
        this.filtroId = filtroId;
    }

    public Long getLabId() {
        return labId;
    }

    public void setLabId(Long labId) {
        this.labId = labId;
    }

    public Long getPredioId() {
        return predioId;
    }

    public void setPredioId(Long predioId) {
        this.predioId = predioId;
    }
}

package org.example.casodeuso1.model;

import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;

import java.time.Instant;
@Measurement(name = "trafego_wifi")
public class WifiData {

    @Column(timestamp = true)
    private Instant time;

    @Column(name = "rssi")
    private Integer rssi;

    @Column(name = "tamanho_bytes")
    private Integer tamanhoBytes;

    @Column(name = "chanel")
    private Integer chanel;

    // TAGS

    @Column(tag = true, name = "esp_sniffer_id")
    private String espSnifferId;

    @Column(tag = true, name = "lab_id")
    private String labId;

    @Column(tag = true, name = "predio_id")
    private String predioId;

    @Column(tag = true, name = "ponto_acesso_id")
    private String pontoAcessoId;

    public WifiData() {
    }

    public Instant getTime() {
        return time;
    }

    public void setTime(Instant time) {
        this.time = time;
    }

    public Integer getRssi() {
        return rssi;
    }

    public void setRssi(Integer rssi) {
        this.rssi = rssi;
    }

    public Integer getTamanhoBytes() {
        return tamanhoBytes;
    }

    public void setTamanhoBytes(Integer tamanhoBytes) {
        this.tamanhoBytes = tamanhoBytes;
    }

    public Integer getChanel() {
        return chanel;
    }

    public void setChanel(Integer chanel) {
        this.chanel = chanel;
    }

    public String getEspSnifferId() {
        return espSnifferId;
    }

    public void setEspSnifferId(String espSnifferId) {
        this.espSnifferId = espSnifferId;
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

    public String getPontoAcessoId() {
        return pontoAcessoId;
    }

    public void setPontoAcessoId(String pontoAcessoId) {
        this.pontoAcessoId = pontoAcessoId;
    }
}

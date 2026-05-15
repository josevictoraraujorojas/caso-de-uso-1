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
    private Long espSnifferId;

    @Column(tag = true, name = "lab_id")
    private Long labId;

    @Column(tag = true, name = "predio_id")
    private Long predioId;

    @Column(tag = true, name = "ponto_acesso_id")
    private Long pontoAcessoId;

    @Column(tag = true, name = "dispositivo_wifi_id")
    private Long dispositivoWifiId;

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

    public Long getEspSnifferId() {
        return espSnifferId;
    }

    public void setEspSnifferId(Long espSnifferId) {
        this.espSnifferId = espSnifferId;
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

    public Long getPontoAcessoId() {
        return pontoAcessoId;
    }

    public void setPontoAcessoId(Long pontoAcessoId) {
        this.pontoAcessoId = pontoAcessoId;
    }

    public Long getDispositivoWifiId() {
        return dispositivoWifiId;
    }

    public void setDispositivoWifiId(Long dispositivoWifiId) {
        this.dispositivoWifiId = dispositivoWifiId;
    }
}

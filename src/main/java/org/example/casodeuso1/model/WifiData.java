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
    private Integer tamanho;

    @Column(name = "canal")
    private Integer canal;

    // TAGS

    @Column(tag = true, name = "esp_sniffer_id")
    private Long espSnifferId;

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

    public Integer getTamanho() {
        return tamanho;
    }

    public void setTamanho(Integer tamanho) {
        this.tamanho = tamanho;
    }

    public Integer getCanal() {
        return canal;
    }

    public void setCanal(Integer canal) {
        this.canal = canal;
    }

    public Long getEspSnifferId() {
        return espSnifferId;
    }

    public void setEspSnifferId(Long espSnifferId) {
        this.espSnifferId = espSnifferId;
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

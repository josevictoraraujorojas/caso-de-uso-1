package org.example.casodeuso1.dto;

public class WifiDataDTO {

    private int type;
    private int subtype;
    private int rssi;
    private int canal;
    private int tamanho;

    private int to_ds;
    private int from_ds;

    private String direcao;

    private String cliente;
    private String bssid;

    private String macAddress;

    private long timestamp;

    public WifiDataDTO() {
    }

    public WifiDataDTO(
            int type,
            int subtype,
            int rssi,
            int canal,
            int tamanho,
            int to_ds,
            int from_ds,
            String direcao,
            String cliente,
            String bssid,
            String macAddress,
            long timestamp
    ) {
        this.type = type;
        this.subtype = subtype;
        this.rssi = rssi;
        this.canal = canal;
        this.tamanho = tamanho;
        this.to_ds = to_ds;
        this.from_ds = from_ds;
        this.direcao = direcao;
        this.cliente = cliente;
        this.bssid = bssid;
        this.macAddress = macAddress;
        this.timestamp = timestamp;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSubtype() {
        return subtype;
    }

    public void setSubtype(int subtype) {
        this.subtype = subtype;
    }

    public int getRssi() {
        return rssi;
    }

    public void setRssi(int rssi) {
        this.rssi = rssi;
    }

    public int getCanal() {
        return canal;
    }

    public void setCanal(int canal) {
        this.canal = canal;
    }

    public int getTamanho() {
        return tamanho;
    }

    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }

    public int getTo_ds() {
        return to_ds;
    }

    public void setTo_ds(int to_ds) {
        this.to_ds = to_ds;
    }

    public int getFrom_ds() {
        return from_ds;
    }

    public void setFrom_ds(int from_ds) {
        this.from_ds = from_ds;
    }

    public String getDirecao() {
        return direcao;
    }

    public void setDirecao(String direcao) {
        this.direcao = direcao;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getBssid() {
        return bssid;
    }

    public void setBssid(String bssid) {
        this.bssid = bssid;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "WifiDataDTO{" +
                "type=" + type +
                ", subtype=" + subtype +
                ", rssi=" + rssi +
                ", canal=" + canal +
                ", tamanho=" + tamanho +
                ", to_ds=" + to_ds +
                ", from_ds=" + from_ds +
                ", direcao='" + direcao + '\'' +
                ", cliente='" + cliente + '\'' +
                ", bssid='" + bssid + '\'' +
                ", macAddress='" + macAddress + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
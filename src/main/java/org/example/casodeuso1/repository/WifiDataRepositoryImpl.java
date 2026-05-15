package org.example.casodeuso1.repository;

import com.influxdb.client.DeleteApi;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.QueryApi;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import org.example.casodeuso1.model.WifiData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class WifiDataRepositoryImpl implements WifiDataRepository {

    private static final String MEASUREMENT = "trafego_wifi";

    private final InfluxDBClient influxDBClient;

    @Value("${influx.bucket}")
    private String bucket;

    @Value("${influx.org}")
    private String org;

    public WifiDataRepositoryImpl(InfluxDBClient influxDBClient) {
        this.influxDBClient = influxDBClient;
    }

    @Override
    public void salvar(WifiData wifiData) {
        wifiData.setTime(Instant.now());

        WriteApiBlocking writeApi = influxDBClient.getWriteApiBlocking();
        writeApi.writeMeasurement(WritePrecision.S, wifiData);
    }

    @Override
    public List<WifiData> listar() {
        return executarConsulta(null, null);
    }

    @Override
    public List<WifiData> buscarPorEsp(Long idEsp) {
        return executarConsulta("esp_sniffer_id", idEsp);
    }

    @Override
    public List<WifiData> buscarPorLab(Long idLab) {
        return executarConsulta("lab_id", idLab);
    }

    @Override
    public List<WifiData> buscarPorPredio(Long idPredio) {
        return executarConsulta("predio_id", idPredio);
    }

    @Override
    public List<WifiData> buscarPorPontoAcesso(Long idPontoAcesso) {
        return executarConsulta("ponto_acesso_id", idPontoAcesso);
    }

    @Override
    public List<WifiData> buscarPorDispositivoWifi(Long idDispositivoWifi) {
        return executarConsulta("dispositivo_wifi_id", idDispositivoWifi);
    }

    @Override
    public void remover(Long idEsp) {
        try {
            OffsetDateTime start = OffsetDateTime.parse("1970-01-01T00:00:00Z");
            OffsetDateTime stop = OffsetDateTime.now();

            String predicate = """
                    _measurement="%s" AND esp_sniffer_id="%s"
                    """.formatted(MEASUREMENT, idEsp);

            DeleteApi deleteApi = influxDBClient.getDeleteApi();
            deleteApi.delete(start, stop, predicate, bucket, org);

            System.out.println("Dados removidos com sucesso.");

        } catch (Exception e) {
            System.err.println("Erro ao remover dados: " + e.getMessage());
        }
    }

    private List<WifiData> executarConsulta(String campo, Object valor) {

        String filtro = montarFiltro(campo, valor);

        String flux = """
                from(bucket: "%s")
                |> range(start: 0)
                |> filter(fn: (r) => r._measurement == "%s")
                %s
                |> pivot(rowKey: ["_time"], columnKey: ["_field"], valueColumn: "_value")
                |> sort(columns: ["_time"], desc: false)
                """.formatted(bucket, MEASUREMENT, filtro);

        QueryApi queryApi = influxDBClient.getQueryApi();
        List<FluxTable> tables = queryApi.query(flux, org);

        List<WifiData> resultado = new ArrayList<>();

        for (FluxTable table : tables) {
            for (FluxRecord record : table.getRecords()) {
                resultado.add(converterRecord(record));
            }
        }

        return resultado;
    }

    private String montarFiltro(String campo, Object valor) {
        if (campo == null || valor == null) {
            return "";
        }

        return """
                |> filter(fn: (r) => r["%s"] == "%s")
                """.formatted(campo, valor);
    }

    private WifiData converterRecord(FluxRecord record) {

        WifiData data = new WifiData();

        data.setTime(record.getTime());

        data.setRssi(toInteger(record.getValueByKey("rssi")));
        data.setTamanhoBytes(toInteger(record.getValueByKey("tamanho_bytes")));
        data.setChanel(toInteger(record.getValueByKey("chanel")));

        data.setEspSnifferId(toLong(record.getValueByKey("esp_sniffer_id")));
        data.setLabId(toLong(record.getValueByKey("lab_id")));
        data.setPredioId(toLong(record.getValueByKey("predio_id")));
        data.setPontoAcessoId(toLong(record.getValueByKey("ponto_acesso_id")));
        data.setDispositivoWifiId(toLong(record.getValueByKey("dispositivo_wifi_id")));

        return data;
    }

    private Long toLong(Object value) {
        if (value == null) {
            return null;
        }

        return Long.parseLong(value.toString());
    }

    private Integer toInteger(Object value) {
        if (value == null) {
            return null;
        }

        return Integer.parseInt(value.toString());
    }
}
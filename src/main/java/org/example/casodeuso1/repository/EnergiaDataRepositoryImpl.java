package org.example.casodeuso1.repository;

import com.influxdb.client.DeleteApi;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.QueryApi;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import org.example.casodeuso1.model.EnergiaData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EnergiaDataRepositoryImpl implements EnergiaDataRepository {

    private static final String MEASUREMENT = "energia";

    private final InfluxDBClient influxDBClient;

    @Value("${influx.bucket}")
    private String bucket;

    @Value("${influx.org}")
    private String org;

    public EnergiaDataRepositoryImpl(InfluxDBClient influxDBClient) {
        this.influxDBClient = influxDBClient;
    }

    @Override
    public void salvar(EnergiaData energiaData) {
        energiaData.setTime(Instant.now());

        WriteApiBlocking writeApi = influxDBClient.getWriteApiBlocking();
        writeApi.writeMeasurement(WritePrecision.S, energiaData);
    }

    @Override
    public List<EnergiaData> listar() {
        return executarConsulta(null, null);
    }

    @Override
    public List<EnergiaData> buscarPorAparelho(Long idAparelho) {
        return executarConsulta("aparelho_id", idAparelho);
    }

    @Override
    public List<EnergiaData> buscarPorEsp(Long idEsp) {
        return executarConsulta("esp_id", idEsp);
    }

    @Override
    public List<EnergiaData> buscarPorFiltro(Long idFiltro) {
        return executarConsulta("filtro_id", idFiltro);
    }

    @Override
    public List<EnergiaData> buscarPorLab(Long idLab) {
        return executarConsulta("lab_id", idLab);
    }

    @Override
    public List<EnergiaData> buscarPorPredio(Long idPredio) {
        return executarConsulta("predio_id", idPredio);
    }

    @Override
    public void remover(Long idEsp) {
        try {
            OffsetDateTime start = OffsetDateTime.parse("1970-01-01T00:00:00Z");
            OffsetDateTime stop = OffsetDateTime.now();

            String predicate = """
                    _measurement="%s" AND esp_id="%s"
                    """.formatted(MEASUREMENT, idEsp);

            DeleteApi deleteApi = influxDBClient.getDeleteApi();
            deleteApi.delete(start, stop, predicate, bucket, org);

            System.out.println("Dados removidos com sucesso.");

        } catch (Exception e) {
            System.err.println("Erro ao remover dados: " + e.getMessage());
        }
    }

    private List<EnergiaData> executarConsulta(String campo, Object valor) {

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

        List<EnergiaData> resultado = new ArrayList<>();

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

    private EnergiaData converterRecord(FluxRecord record) {

        EnergiaData data = new EnergiaData();

        data.setTime(record.getTime());

        data.setCorrente(toDouble(record.getValueByKey("corrente")));
        data.setEnergiaKwh(toDouble(record.getValueByKey("energia_kwh")));
        data.setFatorPotencia(toDouble(record.getValueByKey("fator_potencia")));
        data.setPotencia(toDouble(record.getValueByKey("potencia")));
        data.setTensao(toDouble(record.getValueByKey("tensao")));

        data.setEspId(toLong(record.getValueByKey("esp_id")));
        data.setFiltroId(toLong(record.getValueByKey("filtro_id")));
        data.setLabId(toLong(record.getValueByKey("lab_id")));
        data.setPredioId(toLong(record.getValueByKey("predio_id")));
        data.setMedidorEnergiaId(toLong(record.getValueByKey("medidor_energia_id")));

        return data;
    }

    private Double toDouble(Object value) {
        if (value == null) {
            return null;
        }

        return Double.parseDouble(value.toString());
    }

    private Long toLong(Object value) {
        if (value == null) {
            return null;
        }

        return Long.parseLong(value.toString());
    }
}
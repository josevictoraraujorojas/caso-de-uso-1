package org.example.casodeuso1.mqtt;

import jakarta.annotation.PostConstruct;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.example.casodeuso1.config.MQTTProperties;
import org.example.casodeuso1.dto.*;
import org.example.casodeuso1.model.*;
import org.example.casodeuso1.service.*;
import org.example.casodeuso1.util.DataMapper;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.time.Instant;
import java.util.Date;

@Service
public class MQTTListener {

    private final MqttClient client;
    private final MQTTProperties properties;
    private final ObjectMapper mapper;

    private final MonitorEnergiaService monitorEnergiaService;
    private final SnifferService snifferService;
    private final EnergiaDataService energiaDataService;
    private final WifiDataService wifiDataService;

    private final DispositivoWifiService dispositivoWifiService;
    private final PontoAcessoService pontoAcessoService;

    public MQTTListener(
            MqttClient client,
            MQTTProperties properties,
            MonitorEnergiaService monitorEnergiaService,
            SnifferService snifferService,
            EnergiaDataService energiaDataService,
            DispositivoWifiService dispositivoWifiService,
            PontoAcessoService pontoAcessoService,
            ObjectMapper mapper,
            WifiDataService wifiDataService
    ) {
        this.client = client;
        this.properties = properties;
        this.monitorEnergiaService = monitorEnergiaService;
        this.snifferService = snifferService;
        this.energiaDataService = energiaDataService;
        this.dispositivoWifiService = dispositivoWifiService;
        this.pontoAcessoService = pontoAcessoService;
        this.mapper = mapper;
        this.wifiDataService = wifiDataService;
    }

    @PostConstruct
    private void iniciar() {

        System.out.println("Listener MQTT iniciado");

        try {

            if (!client.isConnected()) {
                client.connect();
            }

            client.setCallback(new MqttCallback() {

                @Override
                public void connectionLost(Throwable throwable) {
                    System.out.println("Conexão perdida: " + throwable.getMessage());
                }

                @Override
                public void messageArrived(String topico, MqttMessage mensagem) {

                    String payload = new String(mensagem.getPayload());

                    System.out.println("TOPICO: " + topico);
                    System.out.println("PAYLOAD: " + payload);

                    try {

                        switch (topico) {

                            case "energia" -> processarEnergia(payload);

                            case "wifi" -> processarWifi(payload);

                            case "monitorEnergia" -> processarMonitor(payload);

                            case "sniffer" -> processarSniffer(payload);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {

                }
            });

            client.subscribe(properties.getTopics().toArray(new String[0]));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* =========================================================
     * ENERGIA
     * ========================================================= */

    private void processarEnergia(String payload) {

        try {

            EnergiaDataDTO dto = mapper.readValue(payload, EnergiaDataDTO.class);

            EnergiaData energiaData = DataMapper.parseObject(dto, EnergiaData.class);

            MonitorEnergia monitorEnergia = buscarMonitorEnergia(dto);

            energiaData.setTime(Instant.now());

            energiaData.setEspId(monitorEnergia.getId());

            validarMonitorEnergia(monitorEnergia);

            energiaData.setMedidorEnergiaId(monitorEnergia.getMedidorEnergia().getId());

            energiaData.setFiltroId(monitorEnergia.getMedidorEnergia().getFiltroLinha().getId());

            energiaData.setLabId(monitorEnergia.getMedidorEnergia().getFiltroLinha().getLaboratorio().getId());

            energiaData.setPredioId(monitorEnergia.getMedidorEnergia().getFiltroLinha().getLaboratorio().getPredio().getId());

            energiaDataService.salvar(energiaData);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void processarMonitor(String payload) {

        try {

            MonitorEnergiaCreateDTO dto = mapper.readValue(payload, MonitorEnergiaCreateDTO.class);

            dto.setDataInstalacao(new Date());

            monitorEnergiaService.salvarOuAtualizar(dto);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private MonitorEnergia buscarMonitorEnergia(EnergiaDataDTO dto) {

        MonitorEnergia monitorEnergia = monitorEnergiaService.buscarPorMac(dto.getEsp32Mac());

        if (monitorEnergia == null) {
            throw new RuntimeException("Monitor de energia não encontrado");
        }

        return monitorEnergia;
    }

    private void validarMonitorEnergia(MonitorEnergia monitorEnergia) {

        if (monitorEnergia.getId() == null) {
            throw new RuntimeException("Monitor de energia sem ID");
        }

        if (monitorEnergia.getMedidorEnergia() == null) {
            throw new RuntimeException("Monitor sem medidor de energia");
        }

        if (monitorEnergia.getMedidorEnergia().getFiltroLinha() == null) {

            throw new RuntimeException("Medidor sem filtro de linha");
        }

        if (monitorEnergia.getMedidorEnergia().getFiltroLinha().getLaboratorio() == null) {

            throw new RuntimeException("Filtro sem laboratório");
        }

        if (monitorEnergia.getMedidorEnergia().getFiltroLinha().getLaboratorio().getPredio() == null) {

            throw new RuntimeException("Laboratório sem prédio");
        }
    }

    /* =========================================================
     * WIFI
     * ========================================================= */

    private void processarWifi(String payload) {

        try {

            WifiDataDTO dto = mapper.readValue(payload, WifiDataDTO.class);

            Sniffer sniffer = buscarSniffer(dto);

            WifiData wifiData = DataMapper.parseObject(dto, WifiData.class);

            String macCliente = dto.getCliente();

            String macAp = dto.getBssid();

            if (macInvalido(macCliente) || macInvalido(macAp)) {

                return;
            }

            DispositivoWifiResponseDTO cliente = buscarOuCriarCliente(macCliente);

            PontoAcessoResponseDTO pontoAcesso = buscarOuCriarPontoAcesso(macAp);

            criarRelacionamento(dto, cliente, pontoAcesso);

            relacionarSniffer(sniffer, cliente, pontoAcesso);

            salvarWifiData(wifiData, cliente, pontoAcesso, sniffer);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private DispositivoWifiResponseDTO buscarOuCriarCliente(String macCliente) {

        DispositivoWifiResponseDTO cliente = dispositivoWifiService.buscarPorMac(macCliente);

        if (cliente == null) {

            DispositivoWifiCreateDTO dto = new DispositivoWifiCreateDTO();

            dto.setEnderecoMac(macCliente);

            cliente = dispositivoWifiService.salvar(dto);
        }

        return cliente;
    }

    private PontoAcessoResponseDTO buscarOuCriarPontoAcesso(
            String macAp
    ) {

        PontoAcessoResponseDTO pontoAcesso = pontoAcessoService.buscarPorMac(macAp);

        if (pontoAcesso == null) {

            PontoAcessoCreateDTO dto = new PontoAcessoCreateDTO();

            dto.setBssid(macAp);

            pontoAcesso = pontoAcessoService.salvar(dto);
        }

        return pontoAcesso;
    }

    private void criarRelacionamento(WifiDataDTO dto, DispositivoWifiResponseDTO cliente, PontoAcessoResponseDTO pontoAcesso) {

        if ("CLIENTE_AP".equals(dto.getDirecao())) {

            dispositivoWifiService.adicionarPontoAcesso(cliente.getId(), pontoAcesso.getId());
        }

        else if ("AP_CLIENTE".equals(dto.getDirecao())) {

            pontoAcessoService.adicionarDispositivoWifi(pontoAcesso.getId(), cliente.getId());
        }
    }

    private void relacionarSniffer(Sniffer sniffer,DispositivoWifiResponseDTO cliente, PontoAcessoResponseDTO pontoAcesso) {

        snifferService.adicionarDispositivoWifi(sniffer.getId(), cliente.getId());

        snifferService.adicionarPontoAcesso(sniffer.getId(), pontoAcesso.getId());
    }

    private void salvarWifiData(WifiData wifiData, DispositivoWifiResponseDTO cliente, PontoAcessoResponseDTO pontoAcesso, Sniffer sniffer
    ) {

        wifiData.setDispositivoWifiId(cliente.getId());

        wifiData.setPontoAcessoId(pontoAcesso.getId());

        wifiData.setEspSnifferId(sniffer.getId());

        wifiData.setPredioId(sniffer.getPredio().getId());

        wifiData.setTime(Instant.now());

        wifiDataService.salvar(wifiData);
    }

    private boolean macInvalido(String mac) {

        return mac == null || mac.equalsIgnoreCase("FF:FF:FF:FF:FF:FF") || mac.equalsIgnoreCase("00:00:00:00:00:00") || mac.startsWith("01:00:5E") || mac.startsWith("33:33");
    }

    /* =========================================================
     * SNIFFER
     * ========================================================= */

    private void processarSniffer(String payload) {

        try {

            SnifferCreateDTO dto = mapper.readValue(payload, SnifferCreateDTO.class);

            dto.setDataInstalacao(new Date());

            snifferService.salvarOuAtualizar(dto);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Sniffer buscarSniffer(WifiDataDTO dto) {

        Sniffer sniffer = snifferService.buscarPorMac(dto.getMacAddress());

        if (sniffer == null) {
            throw new RuntimeException("Sniffer não encontrado");
        }

        if (sniffer.getId() == null) {
            throw new RuntimeException("Sniffer sem ID");
        }

        if (sniffer.getPredio() == null) {throw new RuntimeException("Sniffer sem prédio");
        }

        return sniffer;
    }
}
package org.example.casodeuso1.mqtt;


import jakarta.annotation.PostConstruct;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.example.casodeuso1.config.MQTTProperties;
import org.springframework.stereotype.Service;

@Service
public class MQTTListener {
    private final MqttClient client;
    private final MQTTProperties properties;

    public MQTTListener(MqttClient mqttClient, MQTTProperties mqttProperties) {
        this.client = mqttClient;
        this.properties = mqttProperties;
    }

    @PostConstruct
    private void iniciar(){
        System.out.println("Listener iniciado");
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
                public void messageArrived(String topic, MqttMessage mqttMessage) {
                    System.out.println("Mensagem: " + mqttMessage.toString());
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

                }
            });

            client.subscribe(properties.getTopics().toArray(new String[0]));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

# Procedimento experimental — backend do Caso de Uso 1

O backend consome mensagens MQTT dos tópicos de energia e Wi-Fi. Para este experimento, os logs identificam o recebimento, o processamento, a gravação no InfluxDB e erros. As mensagens são associadas ao mesmo identificador interno `id` durante o processamento síncrono.

## 1. Executar o backend

Na pasta raiz do repositório, use o Java/Maven configurado no ambiente:

```powershell
mvn spring-boot:run
```

Também é possível executar a classe `CasoDeUso1Application` pela IDE. Mantenha visível o console da aplicação.

## 2. Gerar tráfego e observar a entrada

Ligue o ESP32 PZEM e/ou o sniffer com o receptor serial em execução. No console do backend, procure:

```text
[EXPERIMENTO][MQTT_RECEBIDO] id=abcd1234 topico=energia ... payload={...}
```

Para Wi-Fi, o valor de `topico` deve ser `wifi`. O campo `payload` mostra o conteúdo que chegou ao backend por MQTT. Para observar o broker separadamente, use em outro terminal:

```powershell
mosquitto_sub -h HOST_DO_BROKER -p 1883 -t energia -v
mosquitto_sub -h HOST_DO_BROKER -p 1883 -t wifi -v
```

Use o host do broker configurado no seu ambiente. Cada comando observa um tópico; execute-os em terminais separados ou configure uma única assinatura com os tópicos necessários.

## 3. Acompanhar processamento e gravação

Para energia, procure no console as seguintes marcas com o mesmo `id` do backend:

- `[EXPERIMENTO][MQTT_RECEBIDO]`: a mensagem chegou ao listener MQTT;
- `[EXPERIMENTO][INFLUX_INICIO]`: a gravação do measurement `energia` foi iniciada;
- `[EXPERIMENTO][INFLUX_PERSISTIDO]`: a chamada síncrona do InfluxDB retornou sem erro e o log lista os campos e IDs de contexto;
- `[EXPERIMENTO][PROCESSAMENTO_OK]`: o fluxo de processamento de energia foi concluído;
- `[EXPERIMENTO][FALHA_PROCESSAMENTO]` ou `[EXPERIMENTO][INFLUX_FALHA]`: indicam falha na etapa registrada.

Para Wi-Fi, acompanhe os mesmos eventos com measurement `trafego_wifi`. O log `PROCESSAMENTO_OK` também informa os IDs do sniffer, cliente e ponto de acesso após a associação dos dados de contexto.

O identificador `id` é gerado pelo backend ao receber a mensagem; ele não é adicionado ao JSON transmitido pelo ESP32. Para relacionar as etapas, compare esse registro com o payload idêntico mostrado no firmware ou no broker.

## 4. Confirmar independentemente no InfluxDB

Abra o Data Explorer do InfluxDB, selecione o bucket `caso_uso_1` e consulte o measurement `energia` ou `trafego_wifi` no intervalo do experimento. Compare timestamps, valores e tags com a linha `INFLUX_PERSISTIDO`. O log de sucesso indica que a chamada bloqueante de gravação retornou sem erro; a consulta no banco fornece uma evidência independente de que os dados estão disponíveis para leitura.

## 5. Conferir as relações do grafo

No Neo4j Browser, execute consultas exploratórias para verificar os rótulos e tipos de relacionamento realmente existentes:

```cypher
MATCH (n)
RETURN labels(n) AS rotulos, count(*) AS quantidade;
```

```cypher
MATCH (a)-[r]->(b)
RETURN labels(a) AS origem, type(r) AS relacao, labels(b) AS destino, count(*) AS quantidade;
```

Registre os resultados e compare-os com as associações esperadas para os dispositivos, prédios, laboratórios, sniffer, clientes Wi-Fi e pontos de acesso. Nem todo ponto de medição temporal é um nó individual no grafo; as séries de energia e tráfego são armazenadas no InfluxDB.

## 6. Dados experimentais

Repita o procedimento sob condições documentadas e registre: mensagens vistas no broker, mensagens recebidas, processamentos concluídos, gravações confirmadas pelo cliente InfluxDB, registros consultáveis no banco e erros observados. Use as contagens reais para calcular as taxas de recepção e persistência. Não trate um log do firmware como prova isolada de gravação no banco.


## Salvamento dos logs do backend

Execute a aplicação a partir da raiz deste repositório para salvar o console em um arquivo:

```powershell
mvn spring-boot:run 2>&1 | Tee-Object -FilePath experimento-backend.log
```

O arquivo `experimento-backend.log` será criado na pasta atual. Preserve um arquivo por execução e registre o intervalo de tempo do teste. Os logs indicam as operações processadas pela aplicação; confirme separadamente os registros no InfluxDB e as entidades, eventos ou relacionamentos esperados no Neo4j.

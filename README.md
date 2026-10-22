# Backend — Caso de Uso 1: Monitoramento de Energia e Tráfego Wi-Fi

Este repositório integra o primeiro caso de uso do TCC sobre IoT integrado a bancos de dados de séries temporais e bancos orientados a grafos no IF Goiano – Campus Urutaí.

## Objetivo

Receber e processar dados produzidos pelos dispositivos IoT do primeiro caso de uso, relacionados ao monitoramento de energia elétrica e à captura de metadados do tráfego Wi-Fi. O backend faz parte da camada de integração entre as mensagens MQTT, os dados de aplicação e os mecanismos de armazenamento.

## Contexto da arquitetura

O fluxo geral considerado no TCC é:

```text
ESP32 / sensores
      |
      v
 MQTT broker
      |
      v
 Aplicação backend
   |           |
   v           v
InfluxDB     Neo4j
```

O InfluxDB é utilizado para armazenar medições e eventos com referência temporal. O Neo4j representa relações entre entidades, como dispositivos, sensores, ambientes e locais. A associação entre os dados depende dos identificadores e das regras implementadas na aplicação.

## Dados do caso de uso

O primeiro caso de uso contempla duas frentes:

- **Energia elétrica:** leituras obtidas por ESP32 e PZEM-004T, como tensão, corrente, potência, energia acumulada, frequência e fator de potência.
- **Tráfego Wi-Fi:** metadados capturados por ESP32 em modo promíscuo, como RSSI, canal, tamanho do quadro e informações de endereçamento disponíveis.

Os campos efetivamente aceitos, os tópicos consumidos e as rotas REST devem ser conferidos na implementação do projeto, pois precisam corresponder aos firmwares produtores.

## Tecnologias da arquitetura

- Java e Spring Boot, para a camada de aplicação, conforme a implementação do backend;
- MQTT, para receber mensagens dos dispositivos;
- InfluxDB, para dados temporais;
- Neo4j, para representar entidades e relacionamentos.

## Configuração e execução

Antes de executar, configure os endereços de conexão, portas, credenciais e nomes de banco/organização dos serviços utilizados pelo projeto. Utilize variáveis de ambiente ou arquivos locais não versionados para valores sensíveis.

Caso o projeto use Maven, a execução normalmente pode ser feita pela raiz com:

```bash
./mvnw spring-boot:run
```

No Windows, use:

```powershell
.\mvnw.cmd spring-boot:run
```

Confira o `pom.xml`, os arquivos de configuração e as instruções do próprio código para confirmar a versão do Java e os serviços necessários antes de executar.

## Relação com os outros repositórios

- [Firmware de monitoramento de energia](https://github.com/josevictoraraujorojas/pzem-caso-uso1)
- [ESP32 sniffer Wi-Fi](https://github.com/josevictoraraujorojas/sniffer-caso-uso-1)
- [Receptor serial e publicador MQTT](https://github.com/josevictoraraujorojas/sniffer-receptor-caso-uso-1)

## Observação

Este README apresenta o papel arquitetural do repositório no TCC. Para documentar com precisão endpoints, classes, variáveis de ambiente e comandos de execução específicos, essas informações devem ser alinhadas aos arquivos presentes na versão atual do código.

## Procedimento dos testes experimentais

Esta seção documenta a execução e a coleta de evidências dos dois fluxos do Caso de Uso 1: monitoramento de energia e tráfego Wi-Fi. Os testes verificam o caminho entre MQTT, processamento do backend e armazenamento, sem assumir que uma mensagem recebida corresponde necessariamente a um registro persistido.

### Preparação

1. Confira a configuração do broker MQTT, InfluxDB e Neo4j nos arquivos locais de configuração e nas variáveis de ambiente exigidas pelo projeto.
2. Inicie os serviços externos e confirme que estão acessíveis.
3. Execute o backend na raiz do projeto. No Windows, se houver Maven Wrapper:

```powershell
New-Item -ItemType Directory -Force -Path "$HOME\TCC-Experimentos"
.mvnw.cmd spring-boot:run 2>&1 |
  Tee-Object -FilePath "$HOME\TCC-Experimentos\08-backend-caso1.log"
```

Se o wrapper não existir, use `mvn spring-boot:run` com Maven instalado. Inicie o backend antes dos firmwares.

### Cenário A — Energia

- Inicie um assinante MQTT no tópico `energia` e registre a saída em arquivo.
- Execute o firmware PZEM do repositório [pzem-caso-uso1](https://github.com/josevictoraraujorojas/pzem-caso-uso1).
- Aguarde o término indicado pelo firmware e salve o log serial.
- Confira os marcadores do backend e consulte a medição `energia` no bucket `caso_uso_1` do InfluxDB.

### Cenário B — Tráfego Wi-Fi

- Inicie o assinante MQTT no tópico `wifi`.
- Ligue/execute primeiro o ESP32 receptor e depois o ESP32 sniffer dos repositórios correspondentes.
- Salve os logs seriais de ambos os dispositivos.
- Compare os contadores UART/MQTT com o marcador de recebimento do backend e consulte a medição `trafego_wifi` no bucket `caso_uso_1`.

### Instrumentação e leitura dos logs

Procure pelos marcadores:

- `[EXPERIMENTO][MQTT_RECEBIDO]`: o listener do backend recebeu uma mensagem;
- `[EXPERIMENTO][INFLUX_INICIO]`: início da tentativa de escrita no InfluxDB;
- `[EXPERIMENTO][INFLUX_PERSISTIDO]`: a chamada de escrita terminou sem erro reportado;
- `[EXPERIMENTO][PROCESSAMENTO_OK]`: etapa de processamento concluída;
- `[EXPERIMENTO][FALHA_PROCESSAMENTO]` e `[EXPERIMENTO][INFLUX_FALHA]`: falhas registradas.

Use `Select-String -Path "$HOME\TCC-Experimentos\08-backend-caso1.log" -Pattern "\[EXPERIMENTO\]"` para filtrar os marcadores no PowerShell. Para contar recebimentos MQTT registrados no log: `(Select-String -Path "$HOME\TCC-Experimentos\08-backend-caso1.log" -Pattern "\[EXPERIMENTO\]\[MQTT_RECEBIDO\]").Count`.

### Conferência dos resultados

Para cada execução, anote data/hora, cenário, quantidade de mensagens observadas no broker, recebidas pelo backend, escritas sem erro e registros encontrados por consulta no InfluxDB, além de erros. No InfluxDB, confira o bucket `caso_uso_1`, a medição correspondente e o intervalo de tempo da execução. No Neo4j, confira as entidades/relacionamentos esperados para a configuração do caso, distinguindo dados preexistentes daqueles criados durante o teste.

O marcador de escrita bem-sucedida é uma evidência de execução da chamada, não substitui a consulta ao banco. Mensagens MQTT, chamadas de escrita e registros consultados podem ter contagens diferentes. Faça cada repetição com arquivos de log separados.

## Documentação da API (Swagger / OpenAPI)

Após iniciar a aplicação, acesse a documentação interativa:

- **Swagger UI:** http://localhost:8080/swagger-ui/index.html
- **Especificação OpenAPI (JSON):** http://localhost:8080/v3/api-docs

Se a aplicação estiver configurada em outra porta, substitua `8080` pela porta utilizada. O Springdoc identifica os endpoints dos controllers e gera os esquemas dos DTOs usados como parâmetros e respostas.

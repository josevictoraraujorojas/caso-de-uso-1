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

## Documentação da API (Swagger / OpenAPI)

Após iniciar a aplicação, acesse a documentação interativa:

- **Swagger UI:** http://localhost:8080/swagger-ui/index.html
- **Especificação OpenAPI (JSON):** http://localhost:8080/v3/api-docs

Se a aplicação estiver configurada em outra porta, substitua `8080` pela porta utilizada. O Springdoc identifica os endpoints dos controllers e gera os esquemas dos DTOs usados como parâmetros e respostas.

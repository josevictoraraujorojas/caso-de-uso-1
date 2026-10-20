# Procedimento experimental — backend do Caso de Uso 1

## Objetivo
Comparar mensagens observadas na entrada do fluxo com registros exportados do InfluxDB, verificando correspondência de campos e contagens para energia ou tráfego Wi-Fi.

## Captura das mensagens de entrada
Use uma captura por tópico, para não misturar os esquemas. Exemplo para energia:

    mosquitto_sub -h HOST_DO_BROKER -p 1883 -t energia > entrada_energia.jsonl

Para tráfego Wi-Fi:

    mosquitto_sub -h HOST_DO_BROKER -p 1883 -t wifi > entrada_wifi.jsonl

O broker usado na captura deve ser o mesmo ao qual o backend está conectado. Registre horário inicial/final, tópico e quantidade coletada.

## Preparação do arquivo persistido
Exporte do InfluxDB os registros correspondentes ao mesmo intervalo e normalize o resultado para JSONL: um objeto JSON por linha, usando os nomes dos campos de entrada para os valores correspondentes. Não crie registros manualmente e não altere os valores para fazê-los coincidir. Se a aplicação transforma os dados, documente o mapeamento antes de comparar.

## Execução do comparador
Teste as regras locais:

    py tests\avaliar_persistencia.py --autoteste

Energia:

    py tests\avaliar_persistencia.py --tipo energia --entrada entrada_energia.jsonl --persistidos persistidos_energia.jsonl --saida resultado_energia.json

Wi-Fi:

    py tests\avaliar_persistencia.py --tipo wifi --entrada entrada_wifi.jsonl --persistidos persistidos_wifi.jsonl --saida resultado_wifi.json

O relatório apresenta contagens, correspondências, registros ausentes/extras e taxa de integridade por campos. O script retorna código diferente de zero quando encontra divergências.

## Verificação do Neo4j
A inspeção do grafo é complementar: no Neo4j Browser, consulte os rótulos e os relacionamentos existentes no ambiente e compare a estrutura observada com a associação esperada entre dispositivo, ponto de coleta e local. Consultas exploratórias que não pressupõem rótulos específicos:

    MATCH (n) RETURN labels(n) AS rotulos, count(*) AS quantidade;
    MATCH (a)-[r]->(b) RETURN labels(a) AS origem, type(r) AS relacao, labels(b) AS destino, count(*) AS quantidade;

Guarde os resultados da consulta como evidência. Defina antes do teste quais nós e relações deveriam existir.

## Protocolo sugerido
Use cinco repetições por fluxo, com pelo menos 30 mensagens por repetição quando possível. O comparador pressupõe uma relação um-para-um entre mensagens e registros exportados; se o backend agrega ou transforma registros, ajuste o mapeamento e documente essa decisão.

## Limitação
O script não se conecta diretamente ao InfluxDB nem ao Neo4j. Ele compara evidências exportadas e não substitui a inspeção de logs, a verificação da API nem a confirmação física dos dispositivos.

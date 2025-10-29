# Projeto 1: Sistema de Gestão para Restaurante (Semana Vida Plena)

![Status do Projeto](https://img.shields.io/badge/Status-Done-green)

## Descrição do Projeto

Este projeto é um sistema integrado que simula a gestão de diferentes serviços focados em bem-estar: uma Clínica, um Restaurante e um Gerenciador de Eventos. Além da gestão operacional, o ponto forte do sistema é a sua capacidade de gerar **insights valiosos** sobre o comportamento dos participantes, permitindo uma visão estratégica sobre a "jornada do cliente" através dos diferentes estabelecimentos.

## Integrantes 

*   Maria Clara de Oliveira Barbosa
*   Jhon Victor Ramos Martins
*   Maria Luiza da Silva Monteiro

## Funcionalidades Principais

O sistema foi modelado para ser capaz de responder a 9 perguntas de negócio essenciais para a gestão de negócios.

## 📊 Relatórios e Insights de Negócio

Um dos principais diferenciais da "Semana Vida Plena" é a capacidade de responder a perguntas estratégicas, agregando dados de todas as áreas do negócio. Isso permite entender melhor o comportamento dos participantes e otimizar estratégias.

### 1. Engajamento Multifacetado dos Clientes
*   **Pergunta:** Os participantes costumam frequentar mais de um estabelecimento (Clínica, Restaurante, Eventos)? Qual a distribuição dessa frequência?
*   **Objetivo:** Entender o nível de engajamento do cliente com as diferentes ofertas do ecossistema.

### 2. Análise de Gasto por Canal
*   **Pergunta:** Qual é o gasto médio que um participante realiza em cada um dos locais (Clínica, Restaurante, Eventos)?
*   **Objetivo:** Identificar quais serviços geram maior receita por participante engajado.

### 3. Conversão de Conteúdo em Serviço (Saúde)
*   **Pergunta:** Quantos participantes, após participarem de uma palestra de saúde, prosseguem para realizar uma consulta na clínica?
*   **Objetivo:** Avaliar a eficácia de eventos educativos na conversão para serviços especializados.

### 4. Eventos Geradores de Valor para o Restaurante
*   **Pergunta:** Qual evento de origem (associado a um voucher) gera os clientes que mais gastam no restaurante?
*   **Objetivo:** Otimizar campanhas de voucher, identificando eventos que atraem clientes de alto valor para o restaurante.

### 5. Padrões de Uso de Vouchers
*   **Pergunta:** Vouchers de eventos são utilizados com mais frequência logo após o evento ou dias/semanas depois? Qual o tempo médio de uso?
*   **Objetivo:** Compreender o timing de engajamento dos vouchers para planejar promoções e expirações.

### 6. Impacto da Nutrição no Gasto com Alimentação
*   **Pergunta:** O ticket médio no restaurante é maior para participantes que fizeram uma consulta com a Nutricionista?
*   **Objetivo:** Investigar a correlação entre consultas de saúde específicas e o comportamento de consumo em outros serviços.

### 7. Faturamento da "Jornada Plena"
*   **Pergunta:** Qual é o faturamento total médio (considerando consultas, ingressos de eventos e pedidos de restaurante) gerado por um participante que completa a "jornada plena" (passa pelos três locais)?
*   **Objetivo:** Quantificar o valor de um cliente totalmente engajado em todos os serviços.

### 8. Atividade Mais Bem Avaliada
*   **Pergunta:** Qual atividade (Clínica, Evento ou Restaurante) possui a maior avaliação média geral por parte dos participantes?
*   **Objetivo:** Identificar os pontos fortes do negócio do ponto de vista da satisfação do cliente.


### 9. Interesses dos Clientes da "Jornada Plena"
*   **Pergunta:** O perfil de participante que completa a "jornada plena" tende a se inscrever mais em palestras de 'Saúde' ou de 'Culinária'?
*   **Objetivo:** Entender as preferências e direcionar futuras ofertas e eventos para o público mais engajado.


*   **Linguagem:** Java
*   **Ambiente de Desenvolvimento (IDE):** IntelliJ IDEA e Eclipse

## Como Executar o Projeto

Certifique-se de ter o **Java Development Kit (JDK)**, versão 11 ou superior, instalado em sua máquina.

1.  **Clone o repositório:**
    ```bash
    git clone https://github.com/seu-usuario/projeto-poo-restaurante.git
    ```

2.  **Navegue até o diretório do projeto:**
    ```bash
    cd projeto-poo-restaurante
    ```

3.  **Compile os arquivos Java:**
    (Navegue até a pasta `src` antes de compilar)
    ```bash
    cd src
    javac com/semanavidaplena/restaurante/app/Main.java
    ```

4.  **Execute o programa:**
    (Ainda dentro da pasta `src`)
    ```bash
    java com.semanavidaplena.restaurante.app.Main
    ```

##  UML - Diagrama de Classes

![Diagrama de Classes](UML-Diagram-Restaurant.png)

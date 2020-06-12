Api rest tests
======================
[![Build Status](https://travis-ci.org/wlmFincatti/api-rest-tests.svg?branch=master)](https://travis-ci.org/wlmFincatti/api-rest-tests)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=br.com.api-rest%3Ausers&metric=alert_status)](https://sonarcloud.io/dashboard?id=br.com.api-rest%3Ausers)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=br.com.api-rest%3Ausers&metric=coverage)](https://sonarcloud.io/dashboard?id=br.com.api-rest%3Ausers)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=br.com.api-rest%3Ausers&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=br.com.api-rest%3Ausers)

Estrutura de projeto JAVA abrangendo implementação do clean architecture e pirâmide de testes.

## Referência

<p align="center">
  <img src="https://github.com/wlmFincatti/api-rest-tests/blob/master/pyramid-tests.png" width="400 "align="center" />
</p>

O projeto tem como objetivo demonstrar testes unitários com dois frameworks diferentes para testes unitários, no caso Spock baseado em Groovy e o Junit, também foi abordado teste de componente ou aceitação usando o Cucumber e rest Assured para validação de Apis.

A pirâmide de testes implementada nesse projeto têm como referência o documento [Microservice Testing](https://martinfowler.com/articles/microservice-testing/) com algumas adaptações. Basicamente a implementação têm o seguinte princípio:

>**Unitário**: Testa isoladamente as classes, o objetivo desse teste é validar se a regra da classe está funcionando corretamente sem precisar subir qualquer contexto de framework ou integrações.

>**Integração**: Teste que valida a integração, necessita que simule um contexto externo para testar a regra da classe, por exemplo, bancos em memória e contextos de um framework.

>**Componente/Aceitação**: Testa as regras de negócio da aplicação como um todo, mockando todas as depedências. Geralmente são testes que fazem um request e dado os mocks esperam por uma resposta específica.

## Tecnologias
- [Maven](https://maven.apache.org/)
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Swagger](https://swagger.io/)
- [JUnit 5](https://junit.org/junit5/), [Spock](http://spockframework.org/), [Hamcrest](http://hamcrest.org/JavaHamcrest/tutorial) e [Mockito](https://site.mockito.org/) (Testes unitários)
- [Cucumber](https://cucumber.io/), [Rest Assured](http://rest-assured.io/) (Testes de componente)


## Pré-requisitos
- JDK 8
- Maven 3.5.3

## Como utilizar
#### Iniciar a aplicação
```shell
mvn spring-boot:run 
```
Por padrão a aplicação sobe na porta 8080.<br><br>
Swagger: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) <br>

#### Executar testes unitários e componente/aceitação
```shell
mvn clean test
mvn clean verify

```

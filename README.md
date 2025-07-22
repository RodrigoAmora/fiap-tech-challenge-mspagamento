# fiap-tech-challenge-mspagamento
Descrição
---------
Micro-Serviço de Pagamento da fase 4 do Tech Challenge da pós de Arquitetura de Software da FIAP.

Participantes
-------------
* Bruno do Amor Divino da Paixão
* Lucas Matheus Testa
* Rodrigo de Lima Amora de Freitas

Dependências
------------
O projeto usa o Java 17 e as seguintes dependências:

* Spring Boot 3.5.3
* Lombok
* Devtools
* MongoDB
* Swagger
* OpenAPI
* jUnit

Documentação da API
-------------------
A documentação da API pode ser vista através do Swagger e do Redoc.<br>

<b>Documentação da API via Swagger:</b>
```shell script
http://localhost:8080/swagger
```

<b>Documentação da API via Redoc:</b>
```shell script
http://localhost:8080/redoc
```

Banco de dados
--------------
O projeto usa o banco da dados MongoDB.

Gerando o arquivo .jar
----------------------
Para gerar o arquivo <b>.jar</b>, execute o comando via terminal no diretório raiz do projeto:
```shell script
mvn clean install -P{profile} -DskipTests
```

Rodando os testes
-----------------
Para rodar os testes, execute o comando no terminal na raiz do projeto:
```shell script
mvn test
```

Rodando o projeto localmente
----------------------------
Para iniciar a aplicação via IDE, execute a classe `EventostiApplication`

Para iniciar a aplicação via terminal, execute o comando no terminal na raiz do projeto:

```shell script
mvn spring-boot:run
```

Rodando o projeto Docker
------------------------
Para rodar o projeto em um container Docker, primeiro deve-se gerar o .jar de cada um dos projetos.<br>
Após isso, deve-se gerar o build das imagens e subir os containers do Docker.<br><br>
<b>Fazendo o build das imagens:</b>
```shell script
docker-compose build
```

<b>Subindo os containers do Docker:</b>
```shell script
docker-compose up -d
```

##
Para automatizar esse processo, basta executar o Shellscript <b>`docker_build_and_run.sh`</b>:
```shell script
./docker_build_and_run.sh
```

<hr>

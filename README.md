[![codecov](https://codecov.io/gh/seu-usuario/seu-repositorio/branch/main/graph/badge.svg)](https://codecov.io/gh/seu-usuario/seu-repositorio)

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
O projeto usa o MongoDB como banco da dados.

Para rodar o MongoDB localmente, baixe o MongoDB para a sua máquina, descompacte em algum diretório em configure a variável de ambiente MONGO_HOME com o valor `diretório_do_mongo/bin`. <br>
Após isso, no terminal vá até o `diretório_do_mongo/bin` e execute o comoando para iniciar o MongoDB:
```shell script
sudo ./mongod --dbpath /usr/local/var/mongodb
```

##
Após baixar e descompactar o Mongo na sua máquina e cria a variável de ambiente MONGO_HOME, você pode excutar o Shellscript run_mongo na raiz do projeto.
```shell script
./run_mongo.sh
```

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
Para iniciar a aplicação, execute o comando no terminal na raiz do projeto:

```shell script
mvn spring-boot:run
```

Rodando o projeto no Docker
---------------------------
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

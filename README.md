# Pré-requisitos
* Java >= 8
* Docker (Opcional) para executar o processamento como fila. 
Caso não o utilize, basta chamar o endpoint *POST /sheet/process/{id}* para processar o arquivo importado.
 
# Getting Started
    1. A aplicação ouve na porta 8000
    2. O contexto base é /v1/api
    3. O swagger da aplicação está disponível em http://localhost:8000/v1/api/swagger-ui.html

# Executando o projeto
Acesse a pasta do projeto e execute:

No linux/macos:    

```bash
    ./gradlew bootRun
```
No windows:    

```bash
    gradlew.bat bootRun
```

Agora, acesso swagger e veja as APIs disponíveis.

1. Importe uma planilha em POST /file/upload
2. Caso não tenha o rabbit, execute a importação manual POST /sheet/process/{id}
3. Verifique o resultado consultando GET /category e GET /item/category/{id}
4. É possível, também, alterar e exluir itens.

# Endpoints

    1. CategoryController
    1.1. GET /category
    1.2. GET /category/{ID}

    2. FileController
    2.1. POST /file/upload

    3. ItemController
    3.1. GET /item/category/{id}
    3.2. PUT /item/category/{catId}/item
    3.3. DELETE /item/category/{catId}/item/{itemId}
    
    4. SheetController
    4.1. GET /sheet
    4.2. GET /sheet/{id}   
    4.3. GET /sheet//{id}/status
    4.4. POST /sheet/process/{id}
        Este endpoint existe para o caso de não utilização do rabbitmq
                 
# Estrutura do projeto

```bash
+prova-leroy
|
+-docs -> Contém a documentação com os requisitos e comandos
+- src -> Códigos fonte do sistema
    |
    +- main -> Código
        |
        +- configurations -> Configurações do projeto
        +- controllers -> Endpoints
        +- dto -> DTOs (autoexplicativo)
        +- entities -> Entidades
        +- enums -> Enums
        +- exceptions -> Exceções geradas no sistema
        +- repositories -> Repositórios
        +- services -> Interfaces para os serviços
            |
            +- impl -> Implementação dos serviços
    +- test -> Testes
```

# Situação que o projeto visa resolver

Criar uma API RESTful que:

* Receberá uma planilha de produtos (segue em anexo) que deve ser processada em
background (queue).
* Ter um endpoint que informe se a planilha for processada com sucesso ou não.
* Seja possível visualizar, atualizar e apagar os produtos (só é possível criar novos produtos via
planilha). 

# Instalar o rabbit-mq

Para executar o processamento como fila é necessário instalar o rabbit-mq.

##Instale como docker com o comando abaixo
```bash
# docker run -d --hostname sheet-rabbit --name sheet-rabbit-mq -p 15672:15672 -p 5672:5672 rabbitmq
```

Em seguida, logue via console do docker criado com:
```bash
# docker exec -it sheet-rabbit-mq bash
```

E ative o gerenciamento remoto
```bash
$ rabbitmq-plugins enable rabbitmq_management
```
Crie a fila **SheetProcessor** no rabbitmq e a associa-la ao exchange *amq.topic*

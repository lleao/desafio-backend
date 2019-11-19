# Getting Started

### Estrutura do projeto

```bash
+prova-leroy
|
+-docs -> Contém a documentação com os requisitos e comandos
+- src -> Códigos fonte do sistema
    |
    +- main -> Código
        |
        +- controllers -> Endpoints
        +- entities -> Entidades
        +- repositories -> Repositórios
        +- services -> Interfaces para os serviços
            |
            +- impl -> Implementação dos serviços
    +- test -> Testes
```

### Criar uma API RESTful que:

* Receberá uma planilha de produtos (segue em anexo) que deve ser processada em
background (queue).
* Ter um endpoint que informe se a planilha for processada com sucesso ou não.
* Seja possível visualizar, atualizar e apagar os produtos (só é possível criar novos produtos via
planilha). 
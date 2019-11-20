# language: pt

Funcionalidade: Planilha

Cenário: 1 - Lista de planilha
  Dado que já existe um arquivo já importado com sucesso
  Quando consulto a lista de todas as planilhas
  Então ao menos uma planilha é retornada

Cenário: 2 - Obter planilha por id
  Dado que já existe um arquivo já importado com sucesso
  Quando consulto uma planilha existente por id
  Então a categoria é retornada

Cenário: 3 - Obter planilha inesistente
  Dado que já existe um arquivo já importado com sucesso
  Quando consulto uma categoria inexistente por id
  Então o erro de 'Planilha não existe' deve ocorrer

Cenário: 4 - Processar planilha com sucesso
  Dado que já existe um arquivo já importado com sucesso
  E há ao menos uma planilha com status "QUEUED"
  Quando processo a planilha utilizando o id
  Então o novo status da planilha deve ser "DONE"

Cenário: 5 - Processar planilha com erro
  Dado que já existe um arquivo já importado com sucesso
  E há ao menos uma planilha com status "QUEUED"
  Quando processo a planilha utilizando o id
  Então o novo status da planilha deve ser "ERROR"
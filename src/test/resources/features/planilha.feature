# language: pt

Funcionalidade: Planilha

Cenário: 1 - Lista de planilha
  Dado que já existe um arquivo já importado com sucesso
  Quando consulto a lista de todas as planilhas
  Então ao menos uma planilha é retornada

Cenário: 2 - Obter planilha por id
  Dado que já existe um arquivo já importado com sucesso
  Quando consulto uma planilha existente por id
  Então a planilha é retornada

Cenário: 3 - Obter planilha inexistente
  Dado que já existe um arquivo já importado com sucesso
  Quando consulto uma planilha inexistente por id
  Então o erro de 'Planilha não localizada' deve ocorrer

Cenário: 4 - Processar planilha com sucesso
  Dado que já existe um arquivo já importado com sucesso
  E há ao menos uma planilha com status "QUEUED"
  Quando processo a planilha utilizando o id
  Então o novo status da planilha deve ser "DONE"

Cenário: 5 - Processar planilha com erro
  Dado que já existe um arquivo com erro já importado com sucesso
  E há ao menos uma planilha com status "QUEUED"
  E a planilha contém um erro
  Quando processo a planilha utilizando o id
  Então o novo status da planilha deve ser "ERROR"
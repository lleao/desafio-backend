# language: pt
Funcionalidade: Categoria

Cenário: 1 - Lista de categorias
  Dado que já existe um arquivo já importado com sucesso
  Quando consulto a lista de todas as categorias
  Então ao menos uma categoria é retornada

Cenário: 2 - Obter categoria por id
  Dado que já existe um arquivo já importado com sucesso
  Quando consulto uma categoria existente por id
  Então a categoria é retornada

Cenário: 3 - Obter categoria inesistente
  Dado que já existe um arquivo já importado com sucesso
  Quando consulto uma categoria inexistente por id
  Então o erro de 'Categoria não existe' deve ocorrer
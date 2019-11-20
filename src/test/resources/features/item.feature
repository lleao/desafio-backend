# language: pt
Funcionalidade: Item

Cenário: 1 - Lista de item
  Dado que já existe um arquivo já importado com sucesso
  E que há uma categoria com ao menos 2 itens
  Quando consulto a lista de itens pela categoria
  Então ao menos 2 itens são retornados

Cenário: 2 - Atualizar item
  Dado que já existe um arquivo já importado com sucesso
  E que há uma categoria com ao menos 1 itens
  E é adicionado ao final do nome do item "hipt hopt"
  Quando atualizo o item
  Então o item é atualizado com sucesso e o nome contém "hipt hopt"

Cenário: 3 - Atualizar item inesistente
  Dado que já existe um arquivo já importado com sucesso
  E que há uma categoria com ao menos 1 itens
  E há um item inesistente na categoria
  E é adicionado ao final do nome do item "hipt hopt"
  Quando atualizo o item
  Então o erro "Item inesistente" é gerado

Cenário: 4 - Excluir item
  Dado que já existe um arquivo já importado com sucesso
  E que há uma categoria com ao menos 1 itens
  Quando excluo o item
  Então o item é excluído com sucesso


Cenário: 5 - Excluir item inesistente
  Dado que já existe um arquivo já importado com sucesso
  E que há uma categoria com ao menos 1 itens
  E há um item inesistente na categoria
  Quando excluo o item
  Então o erro "Item inesistente" é gerado


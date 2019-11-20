# language: pt

Funcionalidade: Upload

Cenário: 1 - Upload de arquivo com sucesso
  Dado um arquivo válido de importação
  Quando o upload é efetuado
  Então o arquivo é enviado com 'sucesso' e é adicionado a fila

Cenário: 2 - Upload de arquivo com falha
  Dado um arquivo inválido de importação
  Quando o upload é efetuado
  Então  o arquivo é enviado com 'erro'
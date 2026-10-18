# Ajuda Aí 1.0 — TP2 AEDS III

Sistema de perguntas e respostas inspirado no StackOverflow, desenvolvido como segundo trabalho prático da disciplina de Algoritmos e Estruturas de Dados III — PUC Minas.

---

## Participantes

| Nome                       | GitHub    |
|----------------------------|-----------|
| Arthur De Pinho De Almeida | imartzz   |
| Rafael Cardoso Machado     | icm3333   |
| Pedro Henrique Debs Rabelo | —         |
| Artur Otoni Senna Luz      | —         |

---

## Vídeo de demonstração

> [Link do vídeo de demonstração no YouTube - A ser gravado/adicionado]

---

## Sobre o sistema (Novidades do TP2)

No **TP2**, o sistema foi expandido para permitir a interação completa entre a comunidade de usuários:
1. **Busca Paginada de Perguntas Recentes:** Consulta de perguntas de todos os usuários organizadas cronologicamente da mais recente para a mais antiga, implementada sobre uma **Árvore B+** indexada por data de criação (`criacao` decrescente).
2. **Relacionamento N:N (Perguntas e Respostas):** Cada pergunta pode receber múltiplas respostas de diferentes usuários. A entidade `Resposta` estabelece a ligação entre `Usuario` e `Pergunta` utilizando duas **Árvores B+** simultâneas:
   - `relPerguntaResposta` (`idPergunta`, `idResposta`): recupera com alto desempenho todas as respostas associadas a uma pergunta.
   - `relUsuarioResposta` (`idUsuario`, `idResposta`): recupera todas as respostas escritas por um determinado usuário.
3. **Ordenação de Respostas por Nota:** As respostas exibidas para uma pergunta são ordenadas dinamicamente em ordem decrescente de avaliação.
4. **Sistema de Avaliação de Respostas (`Avaliacao`):** Usuários podem votar ("concordar" = +1 ou "discordar" = -1) em respostas de terceiros. O sistema impede auto-voto e votos duplicados, ajustando automaticamente a pontuação e contagem na entidade `Resposta`.
5. **Gestão e Moderação na "Minha Área":**
   - **Minhas perguntas:** Visualização das respostas recebidas para cada pergunta própria, com permissão para o autor da pergunta arquivar respostas que julgar inadequadas.
   - **Minhas respostas:** Visualização de todas as respostas escritas pelo usuário, com opção de arquivamento.
   - **Minhas avaliações:** Consulta dos votos realizados, com possibilidade de cancelamento do voto e estorno automático da pontuação da resposta.

---

## Funcionalidades em desenvolvimento

As seguintes funcionalidades e métodos estão com estrutura pronta e marcações `// implementar`, aguardando a finalização da implementação:

1. **Entidade e Persistência de Avaliações:**
   - `entidades/Avaliacao.java`: serialização e desserialização binária em bytes (`serialize()` e `deserialize()`).
   - `entidades/ArquivoAvaliacao.java`: vinculação nas duas Árvores B+ (`relUsuarioAvaliacao` e `relRespostaAvaliacao`) nas operações de inclusão, consulta por usuário e exclusão.

2. **Regras de Negócio e Controle de Votos:**
   - `controle/ControleAvaliacao.java`:
     - Validação para impedir auto-voto (votar na própria resposta) e votos duplicados pelo mesmo usuário.
     - Registro da avaliação com ajuste atômico de `nota` (+1 ou -1) e incremento de `avaliacoes` na entidade `Resposta`.
     - Cancelamento de avaliação com estorno da pontuação na resposta e remoção do registro.

3. **Interface de Minhas Avaliações:**
   - `visao/VisaoMinhasAvaliacoes.java`: exibição dos votos realizados pelo usuário na área pessoal e fluxo de cancelamento de voto.

---

## Como compilar e executar

```bash
# Na raiz do projeto
javac -encoding UTF-8 aed3/*.java entidades/*.java controle/*.java visao/*.java Principal.java

java Principal
```

---

## Estrutura de classes

### Pacote `aed3/` — Estruturas de dados base
| Classe | Responsabilidade |
|---|---|
| `Arquivo<T>` | CRUD genérico sobre arquivo binário com cabeçalho de registros e lista de excluídos (`primeiroLivre`) |
| `HashExtensivel` | Tabela Hash Extensível para índice direto de usuários por email |
| `ArvoreBMais` | Árvore B+ para índices secundários e relacionamentos 1:N / N:N |
| `ParIDEndereco` | Par (ID → endereço no arquivo) |
| `ParIdId` | Par (ID1, ID2) usado para vincular chaves nas Árvores B+ |
| `ParNomeId` | Par (String, ID) |
| `InterfaceRegistro` | Interface para entidades persistíveis no CRUD genérico |
| `InterfaceArvoreBMais` | Interface para elementos armazenáveis na Árvore B+ |
| `InterfaceHashExtensivel` | Interface para elementos armazenáveis no Hash Extensível |

### Pacote `entidades/` — Entidades e CRUDs especializados
| Classe | Responsabilidade |
|---|---|
| `Usuario` | Entidade usuário: `id`, `nome`, `email`, `hashSenha`, `perguntaSecreta`, `hashRespostaSecreta` |
| `ArquivoUsuario` | Estende `Arquivo<Usuario>`, gerencia índice Hash Extensível por email |
| `Pergunta` | Entidade pergunta: `id`, `idUsuario`, `criacao`, `alteracao`, `nota`, `pergunta`, `palavrasChave`, `ativa` |
| `ArquivoPergunta` | Estende `Arquivo<Pergunta>`, gerencia Árvore B+ de relacionamento com usuário e Árvore B+ de data |
| `ParDataId` | Elemento da Árvore B+ composto por `(dataCriacao, idPergunta)` ordenado de forma decrescente |
| `Resposta` | Entidade resposta: `id`, `idPergunta`, `idUsuario`, `criacao`, `resposta`, `nota`, `avaliacoes`, `ativa` |
| `ArquivoResposta` | Estende `Arquivo<Resposta>`, gerencia duas Árvores B+: `(idPergunta, idResposta)` e `(idUsuario, idResposta)` |
| `Avaliacao` | Entidade avaliação: `id`, `idUsuario`, `idResposta`, `avaliacao` (booleano: true = +1, false = -1) |
| `ArquivoAvaliacao` | Estende `Arquivo<Avaliacao>`, gerencia Árvores B+ para `(idUsuario, idAvaliacao)` e `(idResposta, idAvaliacao)` |
| `ParEmailId` | Par (hash do email → idUsuario) para o Hash Extensível |

### Pacote `controle/` — Lógica de negócio
| Classe | Responsabilidade |
|---|---|
| `ControleUsuario` | Login, cadastro, recuperação de senha e alteração de dados do usuário |
| `ControlePergunta` | Criação, listagem paginada por data, alteração e arquivamento de perguntas |
| `ControleResposta` | Criação de respostas, listagem por pergunta/usuário, arquivamento e ajuste de notas |
| `ControleAvaliacao` | Registro de votos, validação de duplicidade, estorno e cancelamento de avaliações |

### Pacote `visao/` — Interface de terminal
| Classe | Responsabilidade |
|---|---|
| `VisaoLogin` | Tela inicial: login, cadastro e recuperação de senha |
| `VisaoMenuPrincipal` | Navegação principal: acesso à Minha Área e Busca de perguntas |
| `VisaoBuscarPerguntas` | Listagem paginada de perguntas mais recentes, visualização e resposta/voto |
| `VisaoMinhaArea` | Menu da área pessoal do usuário |
| `VisaoMeusDados` | Alteração de dados cadastrais |
| `VisaoPerguntas` | Gestão de perguntas do usuário e moderação de respostas recebidas |
| `VisaoMinhasRespostas` | Visualização e arquivamento de respostas do autor |
| `VisaoMinhasAvaliacoes` | Visualização e cancelamento de votos com estorno de nota |

---

## Operações especiais implementadas

### 1. Listagem Paginada de Perguntas em Ordem Decrescente de Data (`ParDataId` + `ArvoreBMais`)
Para atender à busca por perguntas recentes sem carregar todo o arquivo para a memória ou usar ordenações externas repetitivas, implementamos a classe `ParDataId` que armazena `(long dataCriacao, int idPergunta)`. 
Seu método `compareTo` inverte a ordenação temporal convencional:
```java
if (this.data != outro.data) {
    return this.data > outro.data ? -1 : 1; // datas maiores aparecem nas primeiras folhas da árvore
}
return this.id - outro.id;
```
Dessa forma, ao percorrer as folhas da Árvore B+ (`indiceDataPergunta.read(null)`), as perguntas já são retornadas naturalmente da mais recente para a mais antiga. A paginação (5 por página) é calculada de forma imediata sob o subconjunto de perguntas ativas.

### 2. Relacionamento N:N entre Usuários e Perguntas via Respostas
Uma pergunta possui $N$ respostas e um usuário pode responder a $N$ perguntas. A classe `ArquivoResposta` gerencia duas Árvores B+:
- `relPerguntaResposta`: chave `(idPergunta, idResposta)` — permite recuperar instantaneamente todas as respostas de uma pergunta específica.
- `relUsuarioResposta`: chave `(idUsuario, idResposta)` — permite listar rapidamente todas as respostas de um usuário em sua área pessoal.

### 3. Ordenação das Respostas por Nota Decrescente
Ao exibir as respostas de uma pergunta (tanto na busca pública quanto em "Minhas perguntas"), as respostas recuperadas da Árvore B+ são ordenadas em memória pelo atributo `nota` decrescente, garantindo destaque às respostas com maior saldo de concordâncias pela comunidade.

### 4. Sistema de Avaliações com Controle de Unicidade e Estorno Consistente
A classe `ArquivoAvaliacao` armazena os votos vinculados via Árvores B+ `(idUsuario, idAvaliacao)` e `(idResposta, idAvaliacao)`.
- **Prevenção de duplicidade e auto-voto:** O sistema valida antes de registrar a avaliação se o usuário é o próprio autor da resposta (o que é proibido) e se já existe registro prévio daquele par `(idUsuario, idResposta)`.
- **Estorno em caso de cancelamento:** Quando o usuário cancela seu voto em "Minhas avaliações", a `Avaliacao` é excluída e a pontuação da `Resposta` é revertida atomicamente (`nota -= 1` se o voto foi positivo ou `nota += 1` se o voto foi negativo, decrementando também `avaliacoes`).

### 5. Moderação pelo Autor da Pergunta
O autor da pergunta original possui permissão para visualizar e arquivar respostas de outros usuários recebidas em sua pergunta caso as considere ofensivas ou inadequadas, mantendo a integridade do fórum.


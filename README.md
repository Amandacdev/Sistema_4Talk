# Sistema 4Talk 💬

## Tema:
- Um sistema de chat em que os usuários podem trocar mensagens entre si em conversas individuais ou em grupo.

## Equipe:
- Amanda Cruz de Araújo
- George Barbosa de Lima
- Ian Ribeiro de Mendonça

## Características do projeto:

### Arquitetura:
- Modelo de camadas:
  - Fachada: armazena os métodos acessíveis pela aplicação, seja através do terminal ou interface visual;
  - Repositório: acessado pela fachada, o repositório armazena os objetos criados durante a execução da aplicação;
  - Modelo: agrupa as classes de negócio, acessadas a partir do repositório. Possuem os métodos construtores, getters e setters, entre outros.
    - Classes:
      - Participante
      - Mensnagem
      - Individual
      - Grupo
 

# dsCatalog

![Dscatalog](https://github.com/DennerOl/dsCatalog/assets/124217386/11bf2324-61b5-41c1-af15-9cba4c2d66a9)


Neste projeto iniciei fazendo uma revisão de todo conteudo que aprendi 


●	Banco de dados H2, camadas

●	Criação de entidades

●	Transações e sessão JPA

●	Seeding da base de dados

●	DTO

●	Criando um ambiente de execução no Postman

●	Tratamento de exceções

●	Operações de CRUD

●	Métodos GET, POST, PUT, DELETE

●	Dados de auditoria

●	Paginação

●	Revisão modelo relacional N-N

●	Mapeamento JPA N-N

_______________________>Depois da revisão implementei a parte de Teste automatizados TDD com jUnit e coloquei em prática  seguintes tecnologias:


●	Boas práticas para testes

●	JUnit

●	Testes Java vanilla

●	Padrão de projeto Factory para instanciar objetos

●	Testes com Spring e principais annotations

●	Testes de repository

●	Fixtures no JUnit, BeforeEach

●	Mockito vs MockBean

●	Testes de unidade da camada Service

●	Imports estáticos do Mockito

●	Simulando comportamentos diversos com Mockito

●	Testes na camada web

●	Testes de integração

●	Teste de integração na camada web

_______________________>Em seguida veio a parte de Segurança do projeto com SpringSecurity, as competências foram as seguintes:

⦁	Modelo de dados de usuários e perfis

⦁	Validação com Bean Validation

⦁	Annotations

⦁	Customizando a resposta HTTP

⦁	Validações personalizadas com acesso a banco

⦁	Login e controle de acesso

⦁	Spring Security

⦁	OAuth 2.0

⦁	Token JWT

⦁	Autorização de rotas por perfil

_______________________> Consulta com parametros opcionais 

O usuário informa:

⦁	trecho do nome do produto (opcional)

⦁	categorias de produto desejadas (opcional)

⦁	número da página desejada

⦁	quantidade de itens por página

consulta----> {{host}}/products?page=0&size=12&name=ma&categoryId=1

Resultado-----> O sistema informa uma listagem paginada dos produtos com suas respectivas categorias, conforme os critérios de consulta, ordenados por nome.


![consulta personalizada](https://github.com/DennerOl/dsCatalog/assets/124217386/94cd783e-1f07-46ab-af7e-edbf65ed2a0e)



EXPLICAR NO README 
 caso de recuperar senha do usuario 

1 verificar se email existe
2 gerar um token com uma validade de x minutos e salvar no banco de dados
3 enviar um email para o usuario com um link para usar o token






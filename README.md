# 🚀 Projeto Java - Teste Prático Iniflex

## 📌 Descrição
Este projeto foi desenvolvido como parte do teste prático para a Iniflex, simulando o gerenciamento de funcionários em uma indústria. A aplicação permite operações como inserção, remoção, listagem, cálculos salariais e agrupamento de funcionários, seguindo os requisitos fornecidos.

## 🛠️ Tecnologias Utilizadas
- **Java 17**  
- **Spring Boot**  
- **H2 Database (memória)**  
- **JPA (Hibernate)**  
- **Lombok**  
- **JUnit 5 & AssertJ**  
- **Maven**  

## 📂 Estrutura do Projeto
```plaintext
📦 industria
 ┣ 📂 src/main/java/com/iniflex/industria
 ┃ ┣ 📂 domain
 ┃ ┃ ┣ 📂 model (Entidades)
 ┃ ┃ ┃ ┣ Pessoa.java
 ┃ ┃ ┃ ┣ Funcionario.java
 ┃ ┃ ┣ 📂 repository (Repositórios)
 ┃ ┃ ┃ ┣ FuncionarioRepository.java
 ┃ ┣ 📂 application
 ┃ ┃ ┣ 📂 service (Regras de Negócio)
 ┃ ┃ ┃ ┣ FuncionarioService.java
 ┃ ┣ 📂 presentation
 ┃ ┃ ┣ 📂 controller (API REST)
 ┃ ┃ ┃ ┣ FuncionarioController.java
 ┣ 📂 src/test/java/com/iniflex/industria
 ┃ ┣ FuncionarioServiceTest.java
 ┣ 📜 pom.xml (Gerenciamento do Maven)
 ┣ 📜 README.md (Este arquivo)
```

## ⚙️ Configuração do Banco de Dados
A aplicação usa **H2 Database (memória)**, que já vem configurado. O banco é criado automaticamente ao iniciar a aplicação.

📌 **Console do H2** disponível em:  
🔗 `http://localhost:8080/h2-console`  

📌 **Credenciais do Banco:**  
- **URL:** `jdbc:h2:mem:industria`  
- **Usuário:** `sa`  
- **Senha:** (vazia)

## 🏗️ Como Executar o Projeto
1. **Clone o repositório:**  
```sh
git clone https://github.com/polianabadio/testeiniflex.git
cd testeiniflex
```

2. **Compile o projeto:**  
```sh
mvn clean install
```

3. **Execute a aplicação:**  
```sh
mvn spring-boot:run
```

A API estará disponível em `http://localhost:8080`.

## 📡 Endpoints da API (Testar no Postman)
| Método | Endpoint | Descrição |
|--------|----------|------------|
| **GET** | `/funcionarios` | Lista todos os funcionários |
| **POST** | `/funcionarios` | Adiciona um novo funcionário |
| **DELETE** | `/funcionarios/{nome}` | Remove funcionário pelo nome |
| **PUT** | `/funcionarios/aumentar-salario/{percentual}` | Aumenta o salário de todos em X% |
| **GET** | `/funcionarios/agrupados` | Agrupa funcionários por função |
| **GET** | `/funcionarios/aniversariantes/{meses}` | Lista aniversariantes de determinados meses |
| **GET** | `/funcionarios/mais-velho` | Retorna o funcionário mais velho |
| **GET** | `/funcionarios/ordenados` | Lista funcionários em ordem alfabética |
| **GET** | `/funcionarios/total-salarios` | Retorna o total dos salários |
| **GET** | `/funcionarios/salarios-minimos/{salarioMinimo}` | Calcula salários mínimos por funcionário |

## ✅ Requisitos Implementados
✔ **1. Criar classes Pessoa e Funcionario**  
✔ **2. Implementar CRUD de funcionários**  
✔ **3. Listar, remover e imprimir funcionários corretamente**  
✔ **4. Aplicar aumento salarial de 10%**  
✔ **5. Agrupar funcionários por função**  
✔ **6. Identificar aniversariantes de outubro e dezembro**  
✔ **7. Encontrar funcionário mais velho**  
✔ **8. Ordenar funcionários alfabeticamente**  
✔ **9. Calcular e exibir total dos salários**  
✔ **10. Calcular quantos salários mínimos cada funcionário recebe**  

## 🧪 Testes Automatizados
Os testes foram implementados utilizando **JUnit 5** e **AssertJ**, cobrindo os principais serviços.  
Para rodar os testes:  
```sh
mvn test
```

## 📌 Considerações Finais
- O projeto foi estruturado seguindo boas práticas de **arquitetura limpa** e **DDD**.  
- O banco de dados foi configurado para rodar em memória, permitindo testes rápidos e reset automáticos.  
- Os serviços foram implementados com **Streams API**, garantindo código mais limpo e performático.  
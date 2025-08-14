# 🏆 Badge Conquistado!
<img width="410" height="410" alt="Badge-Literalura" src="https://github.com/user-attachments/assets/10fb6d52-73ba-4063-8726-e15f57551d8d" />

# 📚 Literalura

O **Literalura** é uma aplicação Java para gerenciamento de livros, conectando-se a uma API externa para buscar informações literárias e armazená-las em um banco de dados local.  
O projeto foi desenvolvido como prática de consumo de API, manipulação de dados, persistência e boas práticas de programação orientada a objetos.

---

## 🚀 Funcionalidades

- 🔍 **Buscar livros** por título ou autor usando a API.
- 📄 **Exibir detalhes** dos livros encontrados.
- 💾 **Salvar livros** no banco de dados local.
- 📚 **Listar todos os livros** cadastrados.
- 🏷 **Filtrar por autor, idioma ou ano de publicação**.
- 📊 **Estatísticas** como contagem de livros por autor ou idioma.
- ⚙️ Arquitetura organizada usando princípios de **Clean Code**.

---

## 🛠 Tecnologias Utilizadas

- **Java 17+**
- **Spring Boot** (se aplicável, caso esteja usando)
- **Maven** para gerenciamento de dependências
- **Hibernate/JPA** para persistência de dados
- **PostgreSQL** (ou outro banco que estiver usando)
- **API Gutendex** (ou API utilizada para busca de livros)
- **Lombok** para redução de código boilerplate

---

## 📦 Instalação

### 1️⃣ Clonar o repositório
```bash
git clone https://github.com/seu-usuario/literalura.git
cd literalura
```
### 2️⃣ Configurar o banco de dados
No arquivo application.properties (ou .yml), configure o acesso ao banco:
```bash
spring.datasource.url=jdbc:postgresql://localhost:5432/literalura
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=update
```
### 3️⃣ Instalar dependências
```bash
mvn install
```
4️⃣ Executar o projeto
```bash
mvn spring-boot:run
```

---

📖 Uso
Após iniciar o projeto, você terá acesso a um menu interativo no terminal.
Exemplo de opções disponíveis:
```bash
=== Literalura - Menu ===
1. Buscar livro por título
2. Listar livros cadastrados
3. Filtrar livros por autor
4. Exibir estatísticas
0. Sair
```
### 📂 Estrutura do Projeto

<img width="345" height="352" alt="image" src="https://github.com/user-attachments/assets/d647e125-7c0b-457c-88fb-1aead0f554c3" />

---

### 🔮 Melhorias Futuras
 Criar interface gráfica com JavaFX ou React.

 Adicionar paginação nas consultas.

 Permitir exportação dos dados em PDF ou Excel.

 Implementar autenticação para diferentes usuários.

 ---

 ### 🤝 Contribuindo
Se quiser contribuir com o projeto:

Faça um fork do repositório.

Crie uma branch para sua modificação (git checkout -b minha-feature).

Commit suas alterações (git commit -m 'Minha nova feature').

Envie para seu fork (git push origin minha-feature).

Abra um Pull Request.

---

### 📜 Licença
Este projeto está licenciado sob a MIT License.

---

### 💡 Autor
Desenvolvido por André Alves

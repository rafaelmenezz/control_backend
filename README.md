# Control BACKEND

## Versão java

- **Versão**: 17
- **JDK**: 17
- **SPRING-BOOt-FRAMEWORK**: 3.1.2  

## Database

-	O projeto utiliza MySQL como banco de dados. As migrações de banco de dados necessárias são gerenciadas usando Flyway.

### Instalação

1.  Clone the repository:
    `https://github.com/rafaelmenezz/control_backend.git`
2.  Instalar dependencias mavem;
3.  Criar base de dados ***control_tsc*** no banco de dados MySQL;
4.  Adicionar **username** e **password** no arquivo **application.properties**:

```java
spring.datasource.username=
spring.datasource.password=
```

### API Endpoints

```shellscript
POST /api/auth/login - login do usuário
POST /api/auth/refreshToken - renovar token de acesso
    
GET /api/users  - lista usuarios
GET /api/users/id - Obtem lista de usuários 
POST /api/users - Criar um usuário
PATCH /api/users - Altera um usuário
```

### Usuário padrão
- Credencias do user padrão:
	- **login**:  Matricula(2121) ou CPF(111.111.111-11)
	- **password**: teste

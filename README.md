# ForoHub - API REST para Gestión de Foro

## 📋 Descripción

ForoHub es una API REST desarrollada con Spring Boot para la gestión de un foro donde los usuarios pueden crear, consultar, actualizar y eliminar tópicos (dudas o sugerencias). La API implementa autenticación y autorización con JWT (JSON Web Tokens) para proteger las operaciones sensibles.

## 🚀 Tecnologías Utilizadas

- **Java 17**
- **Spring Boot 3.3.2**
- **Spring Data JPA**
- **Spring Security**
- **MySQL 8**
- **Flyway Migration**
- **JWT (Auth0)**
- **Lombok**
- **Maven**

## 📁 Estructura del Proyecto

```
forohub/
├── src/
│   ├── main/
│   │   ├── java/com/alura/forohub/
│   │   │   ├── controller/          # Controladores REST
│   │   │   ├── model/                # Entidades JPA
│   │   │   ├── repository/           # Repositorios JPA
│   │   │   ├── service/              # Servicios de negocio
│   │   │   ├── dto/                  # DTOs (Data Transfer Objects)
│   │   │   ├── security/             # Configuración de seguridad
│   │   │   ├── infra/exception/      # Manejo de excepciones
│   │   │   └── ForohubApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── db/migration/         # Scripts de migración Flyway
│   └── test/
├── pom.xml
└── README.md
```

## ⚙️ Configuración Previa

### 1. Requisitos del Sistema

- **Java JDK 17** o superior
- **Maven 4** o superior
- **MySQL 8** o superior
- **IDE** (IntelliJ IDEA, Eclipse, VS Code, etc.) - Opcional

### 2. Instalación de MySQL

#### Windows
Descarga el instalador desde: https://dev.mysql.com/downloads/installer/

#### Linux (Ubuntu/Debian)
```bash
sudo apt update
sudo apt install mysql-server
sudo systemctl start mysql
sudo systemctl enable mysql
```

#### macOS
```bash
brew install mysql
brew services start mysql
```

### 3. Configuración de la Base de Datos

1. Accede a MySQL:
```bash
mysql -u root -p
```

2. Crea la base de datos (opcional, la aplicación la crea automáticamente):
```sql
CREATE DATABASE forohub;
```

3. Sal de MySQL:
```sql
EXIT;
```

## 🔧 Instalación y Configuración del Proyecto

### 1. Clonar o Extraer el Proyecto

Si tienes el archivo ZIP:
```bash
unzip forohub.zip
cd forohub
```

### 2. Configurar `application.properties`

Edita el archivo `src/main/resources/application.properties` y ajusta las credenciales de MySQL:

```properties
# Configuración de MySQL
spring.datasource.url=jdbc:mysql://localhost:3306/forohub?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=TU_CONTRASEÑA_MYSQL

# JWT Secret (puedes cambiarlo por tu propia clave secreta)
jwt.secret=mi-clave-secreta-super-segura-para-forohub-2024
jwt.expiration=7200000
```

**Nota:** Reemplaza `TU_CONTRASEÑA_MYSQL` con tu contraseña de MySQL.

### 3. Compilar el Proyecto

```bash
mvn clean install
```

O si prefieres saltarte los tests:
```bash
mvn clean install -DskipTests
```

### 4. Ejecutar la Aplicación

```bash
mvn spring-boot:run
```

O ejecuta directamente el JAR generado:
```bash
java -jar target/forohub-1.0.0.jar
```

La aplicación se ejecutará en: **http://localhost:8080**

## 👥 Usuarios de Prueba

La aplicación incluye 2 usuarios de prueba creados automáticamente:

| Email | Contraseña | Rol |
|-------|------------|-----|
| admin@forohub.com | admin123 | Usuario |
| demo@forohub.com | admin123 | Usuario |

## 📡 Endpoints de la API

### 🔐 Autenticación

#### POST /login
Autentica un usuario y devuelve un token JWT.

**Request:**
```json
{
  "email": "admin@forohub.com",
  "contrasena": "admin123"
}
```

**Response (200 OK):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "tipo": "Bearer",
  "email": "admin@forohub.com"
}
```

---

### 📝 Tópicos

#### POST /topicos
Crea un nuevo tópico. **Requiere autenticación.**

**Headers:**
```
Authorization: Bearer {token}
```

**Request:**
```json
{
  "titulo": "¿Cómo usar Spring Security?",
  "mensaje": "Necesito ayuda para configurar Spring Security en mi proyecto",
  "autorId": 1,
  "curso": "Spring Boot"
}
```

**Response (201 Created):**
```json
{
  "id": 1,
  "titulo": "¿Cómo usar Spring Security?",
  "mensaje": "Necesito ayuda para configurar Spring Security en mi proyecto",
  "fechaCreacion": "2026-03-08T10:30:00",
  "status": "ABIERTO",
  "autorNombre": "Administrador",
  "autorEmail": "admin@forohub.com",
  "curso": "Spring Boot"
}
```

---

#### GET /topicos
Lista todos los tópicos con paginación. **No requiere autenticación.**

**Query Parameters (opcionales):**
- `page`: Número de página (default: 0)
- `size`: Tamaño de página (default: 10)
- `sort`: Campo de ordenamiento (default: fechaCreacion,asc)

**Response (200 OK):**
```json
{
  "content": [
    {
      "id": 1,
      "titulo": "¿Cómo usar Spring Security?",
      "fechaCreacion": "2026-03-08T10:30:00",
      "status": "ABIERTO",
      "autorNombre": "Administrador",
      "curso": "Spring Boot"
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 10
  },
  "totalPages": 1,
  "totalElements": 1
}
```

---

#### GET /topicos/primeros10
Lista los primeros 10 tópicos ordenados por fecha de creación. **No requiere autenticación.**

**Response (200 OK):**
```json
[
  {
    "id": 1,
    "titulo": "¿Cómo usar Spring Security?",
    "fechaCreacion": "2026-03-08T10:30:00",
    "status": "ABIERTO",
    "autorNombre": "Administrador",
    "curso": "Spring Boot"
  }
]
```

---

#### GET /topicos/{id}
Obtiene un tópico específico por ID. **No requiere autenticación.**

**Response (200 OK):**
```json
{
  "id": 1,
  "titulo": "¿Cómo usar Spring Security?",
  "mensaje": "Necesito ayuda para configurar Spring Security en mi proyecto",
  "fechaCreacion": "2026-03-08T10:30:00",
  "status": "ABIERTO",
  "autorNombre": "Administrador",
  "autorEmail": "admin@forohub.com",
  "curso": "Spring Boot"
}
```

---

#### PUT /topicos/{id}
Actualiza un tópico existente. **Requiere autenticación.**

**Headers:**
```
Authorization: Bearer {token}
```

**Request:**
```json
{
  "titulo": "¿Cómo usar Spring Security? [RESUELTO]",
  "mensaje": "Ya resolví mi problema, gracias a todos",
  "status": "RESUELTO",
  "curso": "Spring Boot"
}
```

**Response (200 OK):**
```json
{
  "id": 1,
  "titulo": "¿Cómo usar Spring Security? [RESUELTO]",
  "mensaje": "Ya resolví mi problema, gracias a todos",
  "fechaCreacion": "2026-03-08T10:30:00",
  "status": "RESUELTO",
  "autorNombre": "Administrador",
  "autorEmail": "admin@forohub.com",
  "curso": "Spring Boot"
}
```

---

#### DELETE /topicos/{id}
Elimina un tópico. **Requiere autenticación.**

**Headers:**
```
Authorization: Bearer {token}
```

**Response (200 OK):**
```
(Sin contenido)
```

---

#### GET /topicos/curso/{nombreCurso}
Lista tópicos por nombre de curso. **No requiere autenticación.**

**Example:** `GET /topicos/curso/Spring Boot`

**Response (200 OK):**
```json
[
  {
    "id": 1,
    "titulo": "¿Cómo usar Spring Security?",
    "fechaCreacion": "2026-03-08T10:30:00",
    "status": "ABIERTO",
    "autorNombre": "Administrador",
    "curso": "Spring Boot"
  }
]
```

## 🧪 Pruebas con Insomnia/Postman

### 1. Instalar Insomnia o Postman

- **Insomnia:** https://insomnia.rest
- **Postman:** https://www.postman.com

### 2. Flujo de Prueba Recomendado

1. **Login:** POST a `/login` con credenciales
2. **Copiar el token** de la respuesta
3. **Crear tópico:** POST a `/topicos` con el token en el header
4. **Listar tópicos:** GET a `/topicos`
5. **Obtener tópico:** GET a `/topicos/{id}`
6. **Actualizar tópico:** PUT a `/topicos/{id}` con el token
7. **Eliminar tópico:** DELETE a `/topicos/{id}` con el token

### Ejemplo de Header de Autorización

```
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

## 🔒 Códigos de Estado HTTP

| Código | Descripción |
|--------|-------------|
| 200 OK | Solicitud exitosa |
| 201 Created | Recurso creado exitosamente |
| 400 Bad Request | Error en la solicitud (validación) |
| 401 Unauthorized | Credenciales inválidas |
| 403 Forbidden | Sin autorización (token inválido) |
| 404 Not Found | Recurso no encontrado |
| 500 Internal Server Error | Error interno del servidor |

## 🛡️ Reglas de Negocio

### Tópicos

1. **Todos los campos son obligatorios** al crear un tópico
2. **No se permiten tópicos duplicados** (mismo título y mensaje)
3. **El autor debe existir** en la base de datos
4. **Solo usuarios autenticados** pueden crear, actualizar o eliminar tópicos
5. **Los tópicos tienen un estado** (ABIERTO, CERRADO, RESUELTO, EN_DISCUSION)

### Autenticación

1. **El token JWT expira** después de 2 horas (configurable)
2. **Las credenciales deben ser válidas** (email y contraseña)
3. **El token debe incluirse en el header** Authorization como Bearer token

## 🐛 Solución de Problemas Comunes

### Error de Conexión a MySQL

```
Error: Access denied for user 'root'@'localhost'
```

**Solución:** Verifica que las credenciales en `application.properties` sean correctas.

---

### Error de Puerto 8080 en Uso

```
Error: Port 8080 is already in use
```

**Solución:** Cambia el puerto en `application.properties`:
```properties
server.port=8081
```

---

### Error de Flyway Migration

```
Error: Found non-empty schema(s) without schema history table
```

**Solución:** Limpia la base de datos y reinicia la aplicación:
```sql
DROP DATABASE forohub;
CREATE DATABASE forohub;
```

---

### Token JWT Inválido

```
403 Forbidden
```

**Solución:** 
1. Verifica que el token esté correctamente copiado
2. Asegúrate de incluir "Bearer " antes del token
3. Verifica que el token no haya expirado

## 📚 Recursos Adicionales

- [Documentación de Spring Boot](https://spring.io/projects/spring-boot)
- [Documentación de Spring Security](https://spring.io/projects/spring-security)
- [Documentación de JWT](https://jwt.io/)
- [Documentación de Flyway](https://flywaydb.org/documentation/)

## 👨‍💻 Autor

Proyecto desarrollado para el Challenge ForoHub de Alura Latam.

## 📄 Licencia

Este proyecto es de uso educativo y está disponible bajo la licencia MIT.

---

**¡Disfruta desarrollando con ForoHub!** 🚀

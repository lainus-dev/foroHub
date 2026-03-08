#  ForoHub – API REST de Foro

Challenge Backend

API REST desarrollada como parte del **Challenge ForoHub de Alura Latam**.  
Permite gestionar tópicos de un foro con autenticación segura mediante **JWT**, persistencia en **MySQL** y migraciones automáticas con **Flyway**.

---

##  Tecnologías utilizadas

| Tecnología      | Detalle                          |
|----------------|-----------------------------------|
| Java           | 17                                |
| Spring Boot    | 3.3.2                             |
| Spring Security| JWT (Auth0 java-jwt 4.4.0)        |
| Spring Data JPA| Hibernate 6.5                     |
| Flyway         | Migraciones automáticas          |
| MySQL          | 8.0                               |
| Maven          | Gestión de dependencias           |
| Insomnia       | Pruebas de la API                 |

---

##  Estructura del proyecto

El proyecto sigue una arquitectura en capas: `controller`, `dto`, `model`, `repository`, `security` y `service`.


<img width="1919" height="996" alt="intelliJForoHub" src="https://github.com/user-attachments/assets/253f91e9-de08-4445-a506-6da6d060934d" />

---

##  Configuración y ejecución

### 1. Crear la base de datos

```sql
CREATE DATABASE forohub;
```

### 2. Configurar `application.properties`

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/forohub
spring.datasource.username=TU_USUARIO
spring.datasource.password=TU_PASSWORD
spring.jpa.hibernate.ddl-auto=none
spring.jpa.show-sql=true
spring.flyway.enabled=true
JWT_SECRET=clave-secreta-para-forohub
```

### 3. Ejecutar la aplicación

```bash
mvn spring-boot:run
```

> Flyway creará automáticamente las tablas `usuarios` y `topicos` e insertará los usuarios de prueba al iniciar.

---

##  Base de datos – Tabla `topicos`


<img width="1181" height="765" alt="baseDeDatos" src="https://github.com/user-attachments/assets/3ca43a6d-218b-429a-bc7f-b011c39f7abd" />

---

##  Endpoints de la API

| Método | Endpoint        | Descripción                     | Auth         |
|--------|-----------------|---------------------------------|--------------|
| POST   | `/login`        | Autenticación y Token JWT       | No           |
| POST   | `/topicos`      | Crear un nuevo tópico           | Bearer Token |
| GET    | `/topicos`      | Listar todos los tópicos        | Bearer Token |
| GET    | `/topicos/{id}` | Detallar un tópico por ID       | Bearer Token |
| PUT    | `/topicos/{id}` | Actualizar un tópico existente  | Bearer Token |
| DELETE | `/topicos/{id}` | Eliminar un tópico              | Bearer Token |

---

##  1. Autenticación – Obtener Token JWT

**POST** `/login`

**Request**

```json
{
  "email": "admin@forohub.com",
  "contrasena": "admin123"
}
```

**Respuesta – 200 OK**

```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJmb3JvaHViIiwic3ViIjoiYWRtaW5AZm9yb2h1Yi5jb20iLCJpZCI6MSwibm9tYnJlIjoiQWRtaW5pc3RyYWRvciIsImV4cCI6MTc3MzAxNDM5MX0.MCDjuZvdYwWTrj8i0K-ytrmtdJZq7yyW51VPWMVQfSw",
  "tipo": "Bearer",
  "email": "admin@forohub.com"
}
```

El token obtenido debe enviarse en el header:

```text
Authorization: Bearer <token>
```

<img width="1847" height="983" alt="verToken" src="https://github.com/user-attachments/assets/787c0d71-9f29-49e6-b481-0f1d1030c489" />


---

##  2. Crear tópico

**POST** `/topicos`  _(Requiere Bearer Token)_

**Request**

```json
{
  "titulo": "instalar lamp en ubuntu",
  "mensaje": "pasos para instalar lamp en ubuntu 18",
  "autorId": 2,
  "curso": "sistemas operativos"
}
```

**Respuesta – 201 Created**

```json
{
  "id": 5,
  "titulo": "instalar lamp en ubuntu",
  "mensaje": "pasos para instalar lamp en ubuntu 18",
  "fechaCreacion": "2026-03-08T18:48:29",
  "status": "ABIERTO",
  "autorNombre": "Usuario Demo",
  "autorEmail": "demo@forohub.com",
  "curso": "sistemas operativos"
}
```

<img width="1220" height="465" alt="crearTopico" src="https://github.com/user-attachments/assets/b1cdddb3-d54d-432e-a867-09fba56b432f" />


---

##  3. Listar tópicos

**GET** `/topicos`  _(Requiere Bearer Token)_

Devuelve una respuesta paginada con todos los tópicos registrados en la base de datos.

<img width="1219" height="1031" alt="listarTopico" src="https://github.com/user-attachments/assets/925745b4-9fec-4971-a0fe-87652426f56e" />


---

##  4. Detallar tópico por ID

**GET** `/topicos/{id}`  _(Requiere Bearer Token)_

**Ejemplo de respuesta – 200 OK**

```json
{
  "id": 2,
  "titulo": "Duda sobre Spring Boot",
  "mensaje": "No logro conectar la base de datos correctamente",
  "fechaCreacion": "2026-03-08T18:41:30",
  "status": "ABIERTO",
  "autorNombre": "Administrador",
  "autorEmail": "admin@forohub.com",
  "curso": "Java Backend"
}
```

<img width="1251" height="469" alt="detallarTopico" src="https://github.com/user-attachments/assets/dea806ab-a11a-40f9-b335-9330fd2f00b1" />


---

##  5. Actualizar tópico

**PUT** `/topicos/{id}`  _(Requiere Bearer Token)_

**Request**

```json
{
  "titulo": "Título Actualizado",
  "mensaje": "Este es el nuevo mensaje del tópico",
  "curso": "Java Spring Boot"
}
```

**Respuesta – 200 OK**

```json
{
  "id": 4,
  "titulo": "Título Actualizado",
  "mensaje": "Este es el nuevo mensaje del tópico",
  "fechaCreacion": "2026-03-08T18:46:53",
  "status": "ABIERTO",
  "autorNombre": "Administrador",
  "autorEmail": "admin@forohub.com",
  "curso": "Java Spring Boot"
}
```

<img width="1222" height="471" alt="actualizarTopico" src="https://github.com/user-attachments/assets/d9aac3c4-a00a-44c1-b8bc-fd20fefead33" />


---

##  6. Eliminar tópico

**DELETE** `/topicos/{id}`  _(Requiere Bearer Token)_

Respuesta **200 OK** sin body:

```text
No body returned for response
```

<img width="1236" height="466" alt="borrarTopico" src="https://github.com/user-attachments/assets/54f62fef-ad83-431e-a3dc-47b36b042b43" />


---

## 📊 Lista actualizada tras editar y borrar

Después de las operaciones de actualización y eliminación, la lista refleja los cambios correctamente (`totalElements: 3`).

<img width="1226" height="979" alt="listaEditadoyBorrado" src="https://github.com/user-attachments/assets/d853a5f7-1a59-46e5-b75c-d3b5ff49e2a0" />


---

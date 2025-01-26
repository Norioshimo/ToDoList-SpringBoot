# Autenticación JWT Stateless con Spring Security

Este proyecto es una implementación de autenticación utilizando **JWT (JSON Web Tokens)** con **Spring Security** en un entorno **stateless**. La configuración asegura que no se utilicen sesiones del servidor, lo que lo hace ideal para arquitecturas distribuidas y microservicios.

---

## Características principales
- Autenticación mediante JWT.
- Configuración de seguridad sin estado (**stateless**).
- Rutas públicas y protegidas configuradas mediante Spring Security.
- Uso de **HttpSecurity** y configuración avanzada de filtros.

---

## Tecnologías utilizadas
- **Java 1.8 o superior**
- **Spring Boot 2.7+**
- **Spring Security**
- **JWT (JSON Web Tokens)**
- **Maven** para la gestión de dependencias

---

## Configuración del proyecto

### 1. Requisitos previos
Asegúrese de tener instalados:
- **JDK 1.8** o superior
- **Maven 3.6+**
- Una herramienta de construcción como **IntelliJ IDEA** o **Eclipse**

### 2. Clonar el repositorio
```bash
git clone https://github.com/usuario/repositorio-jwt-stateless.git
cd repositorio-jwt-stateless
```

### 3. Configurar el archivo `application.properties`
Asegúrese de configurar las propiedades necesarias, como la clave secreta para JWT:
```properties
# Configuración de JWT
jwt.secret=tuClaveSecretaSuperSegura
jwt.expirationMs=3600000

# Configuración del servidor
server.port=8080
```

### 4. Construir y ejecutar el proyecto
Para compilar y ejecutar el proyecto, use:
```bash
mvn clean install
mvn spring-boot:run
```

---

## Estructura del proyecto
```
ToDoList-SpringBoot/
├── src/main/java/nsg/portafolio/todolist
│   ├── config/           # Configuraciones de seguridad
│   ├── controllers/      # Controladores REST
│   ├── models/           # Entidades y modelos de datos
│   ├── repository/       # Interfaces para acceso a datos
│   ├── security/         # Implementación de JWT y filtros
│   └── services/         # Lógica de negocio
├── src/main/resources/
│   ├── application.properties
│   └── static/           # Recursos estáticos (si aplica)
└── pom.xml
```

---

## Endpoints disponibles

### Rutas públicas
- `POST /api/auth/login`: Autenticación del usuario y generación de un token JWT.

Para acceder a rutas protegidas, el token JWT debe incluirse en el encabezado de cada solicitud:
```http
Authorization: Bearer <tu_token_jwt>
```

---

## Seguridad
Este proyecto utiliza:
- **SessionCreationPolicy.STATELESS**: Cada solicitud se autentica independientemente, sin mantener estado en el servidor.
- **Filtros personalizados**: Procesan y validan el token JWT.
- **Claves secretas**: Configurables en el archivo `application.properties`.

---
## Base de datos PostgreSQL

El proyecto incluye un script para crear la base de datos y las tablas necesarias en PostgreSQL. Asegúrese de ejecutar el script antes de iniciar el proyecto para crear la estructura de la base de datos correctamente.

## Documentación de Swagger

La documentación de la API está disponible en:
http://localhost:8081/api/swagger-ui/index.html


---

## Contribuciones
Las contribuciones son bienvenidas. Para contribuir:
1. Haga un fork del repositorio.
2. Cree una rama para su función: `git checkout -b nueva-funcionalidad`.
3. Realice los cambios y haga commit: `git commit -m "Agrega nueva funcionalidad"`.
4. Envíe un pull request.

---

## Licencia
Este proyecto está bajo la licencia [MIT](LICENSE). Puedes usarlo y modificarlo libremente.

---

## Autor
Desarrollado por Norio. Si tienes preguntas o sugerencias, no dudes en contactarme.


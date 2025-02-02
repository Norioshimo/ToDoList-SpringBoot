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
- **Java 1.8**
- **Spring Boot 2.7**
- **Spring Security**
- **JWT (JSON Web Tokens)**
- **Maven** para la gestión de dependencias

---

## Configuración del proyecto

### 1. Requisitos previos
Asegúrese de tener instalados:
- **JDK 1.8** 
- **Maven 3.6**
- Una herramienta de construcción como **IntelliJ IDEA** o **Eclipse** o **Netbeans**

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
Para levantar el proyecto una vez compilado:
java -jar target/todolist.jar
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
# Documentación: Paginación en Spring Data JPA

Cuando realizas una consulta paginada en Spring Data JPA, la respuesta devuelta tiene la siguiente estructura JSON:

```json
{
    "content": [],
    "pageable": {
        "sort": {
            "empty": true,
            "sorted": false,
            "unsorted": true
        },
        "offset": 0,
        "pageNumber": 0,
        "pageSize": 10,
        "unpaged": false,
        "paged": true
    },
    "last": true,
    "totalElements": 0,
    "totalPages": 0,
    "size": 10,
    "number": 0,
    "sort": {
        "empty": true,
        "sorted": false,
        "unsorted": true
    },
    "numberOfElements": 0,
    "first": true,
    "empty": true
}
```

## Explicación de los campos

### **1. `content`**
- Contiene la lista de elementos de la página actual.
- Si está vacío (`[]`), significa que no hay datos en la página consultada.

### **2. `pageable`** (Información sobre la paginación)
- **`sort`**: Información sobre el ordenamiento de los datos.
  - `empty`: `true` si no hay criterios de ordenamiento definidos.
  - `sorted`: `true` si los datos están ordenados.
  - `unsorted`: `true` si los datos no están ordenados.
- **`offset`**: Índice del primer elemento de la página en relación con el total de elementos.
- **`pageNumber`**: Número de la página actual (empieza desde `0`).
- **`pageSize`**: Cantidad de elementos por página.
- **`unpaged`**: `true` si la paginación está deshabilitada.
- **`paged`**: `true` si la paginación está habilitada.

### **3. `last`**
- `true` si esta es la última página disponible.

### **4. `totalElements`**
- Cantidad total de elementos en todas las páginas.

### **5. `totalPages`**
- Número total de páginas disponibles.

### **6. `size`**
- Cantidad de elementos que se mostraron en la página actual (igual a `pageSize`).

### **7. `number`**
- Número de la página actual (basado en `0`).

### **8. `sort`** (Información de ordenamiento aplicada a la página actual)
- **`empty`**: `true` si no hay ordenamiento.
- **`sorted`**: `true` si hay ordenamiento aplicado.
- **`unsorted`**: `true` si no hay ordenamiento aplicado.

### **9. `numberOfElements`**
- Cantidad de elementos en la página actual.

### **10. `first`**
- `true` si esta es la primera página.

### **11. `empty`**
- `true` si la página actual no tiene elementos.

## **Ejemplo de uso en código**

Si llamas a un repositorio paginado en Spring Boot:

```java
Pageable pageable = PageRequest.of(0, 10);
Page<MiEntidad> page = miEntidadRepository.findAll(pageable);
```

Y lo serializas en una API REST con Spring Boot, obtendrás una respuesta JSON con esta estructura.

---

Este documento proporciona una referencia clara sobre la estructura de la paginación en Spring Data JPA y puede ser agregado al `README.md` de tu proyecto. 🚀



---

## Licencia
Este proyecto está bajo la licencia [MIT](LICENSE). Puedes usarlo y modificarlo libremente.

---

## Autor
Desarrollado por Norio. Si tienes preguntas o sugerencias, no dudes en contactarme.


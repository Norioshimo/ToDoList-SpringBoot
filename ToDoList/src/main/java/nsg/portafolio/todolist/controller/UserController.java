package nsg.portafolio.todolist.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import nsg.portafolio.todolist.dto.LoginDto;
import nsg.portafolio.todolist.dto.ResponseWrapper;
import nsg.portafolio.todolist.model.Tasks;
import nsg.portafolio.todolist.model.Users;
import nsg.portafolio.todolist.service.UsersServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@Tag(name = "Users API", description = "Endpoints para la gestión de usuarios")
public class UserController implements Serializable {

    @Autowired
    private UsersServices usersService;

    @GetMapping("/hello")
    public String sayHello() {
        return "¡Hola desde Spring Boot 2.7.12 con Java 1.8!";
    }

    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody Users user) {

        try {
            Users userExiset = usersService.findByEmail(user.getEmail());

            if (userExiset != null) {
                System.out.println("Existe el usuario con el mail " + user.getEmail());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ResponseWrapper<String>("Existe el usuario con el mail " + user.getEmail()));
            }

            Users createdUser = usersService.create(user); // Crear cada usuario

            if (createdUser != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(createdUser); // Devolver la lista de nuevos usuarios
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ResponseWrapper<String>("Hubo error al crear usuario: " + user.getEmail()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseWrapper<String>("Hubo un error inesperado al procesar el usuario: " + user.getEmail() + ". " + e));
        }
    }

    @PutMapping
    public ResponseEntity<Object> update(@RequestBody Users users) {
        usersService.update(users);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener usuario por ID", description = "Devuelve un usuario específico por su ID")
    public ResponseEntity<?> findById(@PathVariable("id") Integer idUsers) {
        Users users = usersService.findById(idUsers);
        if (users != null) {
            return ResponseEntity.status(HttpStatus.OK).body(users);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseWrapper<String>("No existe el usuario con el id " + idUsers));
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> findByEmail(@PathVariable("email") String email) {
        Users users = usersService.findByEmail(email);
        if (users != null) {
            return ResponseEntity.status(HttpStatus.OK).body(users);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseWrapper<String>("No existe el usuario con el mail " + email));
        }
    }

    @GetMapping
    public ResponseEntity<Object> findAll(
            @RequestParam(name = "limit", defaultValue = "10") Integer limit,
            @RequestParam(name = "offset", defaultValue = "0") Integer offset
    ) {
        System.out.println("limit: " + limit + " | offset: " + offset);

        Pageable pageable = PageRequest.of(offset, limit);

        Page<Users> page = this.usersService.findAll(pageable);

        return ResponseEntity.status(HttpStatus.OK).body(page);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper<String>> delete(@PathVariable("id") Integer idUsers) {
        Users users = usersService.delete(idUsers);
        if (users != null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseWrapper<String>("El usuario con id " + idUsers + " se ha eliminado correctamente"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseWrapper<String>("El usuario no existe"));
        }
    }

    @PostMapping("/login")
    @Operation(summary = "Authenticar por Usuario y Clave", description = "Devuelve el token")
    public ResponseEntity<Object> login(@RequestBody LoginDto request) {
        // Aquí deberías llamar al servicio que maneja la lógica de autenticación
        ResponseWrapper<String> response = usersService.login(request);

        if (response != null) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseWrapper<String>("Credenciales incorrectas."));
        }
    }

    @GetMapping("/validate-token")
    public ResponseEntity<ResponseWrapper<String>> validateToken(@RequestParam(name = "token", defaultValue = "") String token) {

        if (token.isEmpty()) {
            System.out.println("Token no enviado.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseWrapper<String>("token es campo requerido."));
        }

        // Validar el token y obtener el correo si es válido
        String userEmail = usersService.getUserEmailFromToken(token);

        if (userEmail != null) {
            // Si el correo no es null, significa que el token es válido
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<String>("Token válido. Correo: " + userEmail));

        } else {
            // Si el correo es null, el token es inválido
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseWrapper<String>("Token inválido."));

        }
    }
}

package nsg.portafolio.todolist.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.Serializable;
import javax.persistence.EntityNotFoundException;
import nsg.portafolio.todolist.dto.ResponseWrapper;
import nsg.portafolio.todolist.model.Tasks;
import nsg.portafolio.todolist.model.Users;
import nsg.portafolio.todolist.service.TasksServices;
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
@RequestMapping("/tasks")
@Tag(name = "Tasks API", description = "Endpoints para la gestión de tareas")
public class TasksController implements Serializable {

    @Autowired
    private TasksServices tasksService;

    @Autowired
    private UsersServices usersService;

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody Tasks task, @RequestParam Integer userId) {
        Users usuario = usersService.findById(userId);

        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseWrapper<String>("El usuario especificado no existe."));
        }

        task.setUsersId(usuario);

        Tasks newTasks = tasksService.create(task);

        if (newTasks != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(newTasks);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseWrapper<String>("Hubo un error al procesar su solicitud. Por favor, inténtelo de nuevo más tarde."));
        }
    }

    @PutMapping
    public ResponseEntity<Object> update(@RequestBody Tasks tasks) {
        try {
            Tasks updatedTask = tasksService.update(tasks);
            return new ResponseEntity<>(updatedTask, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(new ResponseWrapper<String>(e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseWrapper<String>(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Integer idTasks) {
        Tasks tasks = tasksService.findById(idTasks);
        if (tasks != null) {
            return ResponseEntity.status(HttpStatus.OK).body(tasks);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseWrapper<String>("No se encontró ninguna tarea con el ID proporcionado."));
        }
    }

    @GetMapping
    public ResponseEntity<Object> findAll(
            @RequestParam(name = "limit", defaultValue = "10") Integer limit,
            @RequestParam(name = "offset", defaultValue = "0") Integer offset
    ) {
        System.out.println("limit: " + limit + " | offset: " + offset);

        Pageable pageable = PageRequest.of(offset, limit);

        Page<Tasks> page = this.tasksService.findAll(pageable);

        return ResponseEntity.status(HttpStatus.OK).body(page);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") Integer idTasks) {
        Tasks tasks = tasksService.delete(idTasks);
        if (tasks != null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseWrapper<String>("La tarea con id " + idTasks + " se ha eliminado correctamente"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseWrapper<String>("La tarea no existe"));
        }
    }

}

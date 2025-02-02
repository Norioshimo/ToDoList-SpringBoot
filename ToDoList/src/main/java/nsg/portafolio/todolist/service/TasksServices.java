package nsg.portafolio.todolist.service;

import java.util.List;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import nsg.portafolio.todolist.model.Tasks;
import nsg.portafolio.todolist.repository.TasksRepository;
import nsg.portafolio.todolist.service.interfaces.ITasksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TasksServices implements ITasksService {

    @Autowired
    private TasksRepository tasksRepository;

    @Override
    public Tasks create(Tasks tasks) {
        return tasksRepository.save(tasks);
    }

    @Override
    public Tasks update(Tasks tasks) {

        Optional<Tasks> existingTask = tasksRepository.findById(tasks.getTasksId());

        if (existingTask.isPresent()) {

            Tasks taskToUpdate = existingTask.get();
            taskToUpdate.setTitulo(tasks.getTitulo());
            taskToUpdate.setDescripcion(tasks.getDescripcion());

            taskToUpdate.setUsersId(tasks.getUsersId());

            return tasksRepository.save(taskToUpdate);
        } else {
            throw new EntityNotFoundException("No existe tarea con el id " + tasks.getTasksId());
        }
    }

    @Override
    public Tasks findById(Integer id) {
        Optional<Tasks> tasksOptional = tasksRepository.findById(id);
        return tasksOptional.orElse(null);
    }

    @Override
    public List<Tasks> findAll() {
        return tasksRepository.findAll();
    }

    @Override
    public Page<Tasks> findAll(Pageable pageable) {
        return tasksRepository.findAll(pageable);
    }

    @Override
    public Tasks delete(Integer id) {
        Optional<Tasks> tasksOptional = tasksRepository.findById(id);
        if (tasksOptional.isPresent()) {
            Tasks tasks = tasksOptional.get();
            tasksRepository.deleteById(id);
            return tasks;
        }
        return null;
    }

}

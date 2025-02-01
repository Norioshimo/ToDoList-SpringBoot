package nsg.portafolio.todolist.service.interfaces;

import java.util.List;
import nsg.portafolio.todolist.model.Tasks;

public interface ITasksService {

    Tasks create(Tasks tasks);

    Tasks update(Tasks tasks);

    Tasks findById(Integer id);

    List<Tasks> findAll();

    Tasks delete(Integer id);
}

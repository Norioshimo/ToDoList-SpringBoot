package nsg.portafolio.todolist.service.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import nsg.portafolio.todolist.model.Tasks;

public interface ITasksService {

    Tasks create(Tasks tasks);

    Tasks update(Tasks tasks);

    Tasks findById(Integer id);

    List<Tasks> findAll();

    Page<Tasks> findAll(Pageable pageable);

    Tasks delete(Integer id);
}

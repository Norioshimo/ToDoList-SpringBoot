 
package nsg.portafolio.todolist.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import nsg.portafolio.todolist.model.Tasks;
import org.springframework.data.jpa.repository.JpaRepository;

 
public interface TasksRepository extends JpaRepository<Tasks, Integer>{
    Page<Tasks>findAll(Pageable pageable);
}

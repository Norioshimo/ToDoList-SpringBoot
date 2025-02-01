 
package nsg.portafolio.todolist.repository;

import nsg.portafolio.todolist.model.Tasks;
import org.springframework.data.jpa.repository.JpaRepository;

 
public interface TasksRepository extends JpaRepository<Tasks, Integer>{
    
}

package nsg.portafolio.todolist.repository;

import nsg.portafolio.todolist.model.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Integer> {

    Users findByEmail(String email);
    Page<Users>findAll(Pageable pageable);
}

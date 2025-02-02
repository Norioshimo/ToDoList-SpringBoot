package nsg.portafolio.todolist.service.interfaces;

import java.util.List;
import nsg.portafolio.todolist.dto.LoginDto; 
import nsg.portafolio.todolist.dto.ResponseWrapper;
import nsg.portafolio.todolist.model.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUsersService {

    Users create(Users user);

    Users update(Users user);

    Users findById(Integer id);

    Users findByEmail(String email);

    List<Users> findAll();
    
    Page<Users> findAll(Pageable pageable);

    Users delete(Integer id);

    ResponseWrapper login(LoginDto request);

    boolean validateToken(String token);

    String getUserEmailFromToken(String token);
}

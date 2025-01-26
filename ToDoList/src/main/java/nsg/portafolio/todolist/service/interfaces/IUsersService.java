package nsg.portafolio.todolist.service.interfaces;

import java.util.List;
import nsg.portafolio.todolist.dto.LoginDto;
import nsg.portafolio.todolist.dto.ResponseDto;
import nsg.portafolio.todolist.model.Users;

public interface IUsersService {

    Users create(Users user);

    Users update(Users user);

    Users findById(Integer id);

    Users findByEmail(String email);

    List<Users> findAll();

    Users delete(Integer id);

    ResponseDto login(LoginDto request);

    boolean validateToken(String token);

    String getUserEmailFromToken(String token);
}

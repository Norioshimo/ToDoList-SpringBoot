package nsg.portafolio.todolist.service;

import java.util.List;
import java.util.Optional;
import nsg.portafolio.todolist.dto.LoginDto;
import nsg.portafolio.todolist.dto.ResponseDto;
import nsg.portafolio.todolist.model.Users;
import nsg.portafolio.todolist.repository.UsersRepository;
import nsg.portafolio.todolist.security.TokenService;
import nsg.portafolio.todolist.service.interfaces.IUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsersServices implements IUsersService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;

    @Override
    public Users create(Users user) {
        // Cifrar la contraseña antes de guardar
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return usersRepository.save(user);
    }

    @Override
    public Users update(Users users) {
        return usersRepository.save(users);
    }

    @Override
    public Users findById(Integer id) {
        Optional<Users> usersOptional = usersRepository.findById(id);
        return usersOptional.orElse(null);
    }

    @Override
    public List<Users> findAll() {
        return usersRepository.findAll();
    }

    @Override
    public Users delete(Integer id) {
        Optional<Users> usersOptional = usersRepository.findById(id);
        if (usersOptional.isPresent()) {
            Users users = usersOptional.get();
            usersRepository.deleteById(id);
            return users;
        }
        return null;
    }

    @Override
    public ResponseDto login(LoginDto request) {
        // 1. Obtener el usuario por email
        Users users = usersRepository.findByEmail(request.getEmail());

        // 2. Verificar si el usuario existe y si la contraseña es correcta
        if (users != null && passwordEncoder.matches(request.getPassword(), users.getPassword())) {
            // 3. Generar un token (puedes usar JWT u otra estrategia)
            String token = tokenService.generateToken(users); // Implementa este método para generar el token

            // 4. Crear y devolver la respuesta de login
            return new ResponseDto(token, "Login successful");
        }
//        // 5. Devolver null o lanzar una excepción si la autenticación falla
        return null;
    }

    @Override
    public boolean validateToken(String token) {
        return tokenService.validateToken(token);
    }

    @Override
    public Users findByEmail(String email) {
        return usersRepository.findByEmail(email);
    }

    @Override
    public String getUserEmailFromToken(String token) {
        return tokenService.getUserEmailFromToken(token);
    }

}

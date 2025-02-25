package nsg.portafolio.todolist.dto;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {

    @NotNull(message = "Email es requerido")
    private String email;

    @NotNull(message = "Password es requerido")
    private String password;
}

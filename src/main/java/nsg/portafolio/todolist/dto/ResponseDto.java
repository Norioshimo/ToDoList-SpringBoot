package nsg.portafolio.todolist.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDto {

    private String valor; // El valor
    private String message; // Mensaje de respuesta (opcional)
}

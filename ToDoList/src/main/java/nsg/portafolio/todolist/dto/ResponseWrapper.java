package nsg.portafolio.todolist.dto;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResponseWrapper<T> implements Serializable {

    private T data;
    private String message;

    public ResponseWrapper() {
        this.message = null;
        this.data = null;
    }

    public ResponseWrapper(String message) {
        this.message = message;
        this.data = null;
    }

    public ResponseWrapper(T data, String message) {
        this.message = message;
        this.data = data;
    }
}

package nsg.portafolio.todolist.security;

import java.security.SecureRandom;
import java.util.Base64;

public class SecretKeyGenerator {

    /**
     * Clase auxiliar para generar clave secreta para el JWT
     * @param args 
     */
    public static void main(String[] args) {
        
        System.out.println("Generar clave secreta para el JWT");
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[64]; // 64 bytes para 512 bits
        secureRandom.nextBytes(randomBytes);
        String secretKey = Base64.getEncoder().encodeToString(randomBytes);
        System.out.println("Clave secreta generada: " + secretKey);
    }
}

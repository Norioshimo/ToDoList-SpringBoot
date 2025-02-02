package nsg.portafolio.todolist.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Base64;
import java.util.Date;
import javax.annotation.PostConstruct;
import nsg.portafolio.todolist.model.Users;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    @Value("${jwt.secret}")
    private String secretKeyString; // Mantenerlo como String

    private byte[] secretKey; // Agregar este atributo

    @Value("${jwt.expiration}")
    private long expirationTime;

    @PostConstruct
    public void init() {
        this.secretKey = Base64.getDecoder().decode(secretKeyString); // Decodifica a byte[]
    }

    /**
     * Método para generar un token JWT
     *
     * @param users
     * @return
     */
    public String generateToken(Users users) {
        Claims claims = Jwts.claims().setSubject(users.getEmail()).build();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationTime);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    /**
     * Método para validar el token
     *
     * @param token
     * @return
     */
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);

            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException e) {
            // Registrar la excepción para facilitar la depuración
            System.out.println("Token validation error: " + e.getMessage());
            return false;
        }
    }

    /**
     * Método para obtener el correo del usuario a partir del token
     *
     * @param token
     * @return
     */
    public String getUserEmailFromToken(String token) {
        try {
            return this.extractClaims(token).getSubject(); // Devuelve el correo del usuario
        } catch (JwtException e) {
            // Registrar la excepción para facilitar la depuración
            System.out.println("Error retrieving user email from token: " + e.getMessage());
            return null;
        }
    }

    /**
     * Método para extraer los claims del token JWT
     *
     * @param token
     * @return
     */
    public Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Método para extraer el username del token
     *
     * @param token
     * @return
     */
    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    /**
     * Método para verificar si el token ha expirado
     *
     * @param token
     * @return
     */
    public boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    /**
     * Método para generar un token si es válido
     *
     * @param token
     * @param username
     * @return
     */
    public boolean validateToken(String token, String username) {
        return (username.equals(extractUsername(token)) && !isTokenExpired(token));
    }

}

package nsg.portafolio.todolist.security;

import io.jsonwebtoken.ExpiredJwtException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.http.HttpServletResponse;
import nsg.portafolio.todolist.dto.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    private static final List<String> EXCLUDED_PATHS = Arrays.asList(
            "swagger", "/v3/api-docs", "/webjars/**", 
            "/users/hello", "/users/login", "/users/create", "/users/validate-token"
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request, javax.servlet.http.HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("doFilterInternal");

        String path = request.getServletPath();
        System.out.println("PATH: " + path + " | Validar: " + EXCLUDED_PATHS.contains(path));
        System.out.println("paths: " + EXCLUDED_PATHS.toString());

        if (EXCLUDED_PATHS.stream().anyMatch(path::contains)) {
            System.out.println("No validar la ruta " + path);
            filterChain.doFilter(request, response);
            return;
        }

        final String token = request.getHeader("Authorization");
        System.out.println("Token: " + token);

        String username = null;

        if (token != null && token.startsWith("Bearer ")) {
            try {
                // Extraer el JWT y verificarlo
                username = tokenService.extractUsername(token.substring(7));
            } catch (ExpiredJwtException e) {
                System.out.println("JWT token is expired " + e);
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // Código HTTP 401
                response.getWriter().write("Token expirado");
                return;
            } catch (Exception e) {
                System.out.println("Error in JWT token " + e);
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // Código HTTP 401
                response.getWriter().write("Error inesperado, vuelva a iniciar sesión. " + e);
                return;
            }
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // Código HTTP 401
            response.getWriter().write("Token no envíado en la cabecera. Auth Type. Bearer Token");
            return;
        }

        // Si se encuentra un username en el token, establece la autenticación en el contexto de seguridad
        if (username != null) {
            // Aquí puedes agregar más lógica para configurar la autenticación de usuario
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>()));
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // Código HTTP 401
            response.getWriter().write("Token invalido.");
            return; // Detener el procesamiento de la solicitud
        }

        filterChain.doFilter(request, response);
    }
}

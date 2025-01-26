package nsg.portafolio.todolist.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private final JwtRequestFilter jwtRequestFilter;

    public SecurityConfig(JwtRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        System.out.println("Configure SecurityConfig");
        http
                .csrf().disable()
                .authorizeRequests()
                //                .antMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**", "/webjars/**").permitAll() // Permitir Swagger
                //                .antMatchers("/users/hello", "/users/login", "/users/create", "/users/validate-token").permitAll() // Permitir acceso sin autenticación a ciertos endpoints
                .anyRequest().permitAll() // Permitir acceso sin autenticación
                //                .anyRequest().authenticated() // Requiere autenticación para cualquier otra solicitud
                .and()
                .formLogin().disable()// Desactivar el formulario de inicio de sesión 
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)// Stateless para JWT
                //                .httpBasic(Customizer.withDefaults()); // Añadir autenticación básica
                ;
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class); // Filtro JWT

    }

}

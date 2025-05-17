package nsg.portafolio.todolist.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.http.HttpHeaders;

@OpenAPIDefinition(
        info = @Info(
                title = "API TODO LIST",
                description = "Documentación de la api de todo list",
                version = "1.0.0",
                contact = @Contact(
                        name = "Norio",
                        email = "developer.test.love@gmail.com"
                )
        ),
        servers = {
            @Server(
                    description = "DEV SERVER",
                    url = "http://localhost:8081/api"
            )
            ,
                @Server(
                    description = "DEV SERVER DOCKER",
                    url = "http://localhost:8080/api"
            )
            ,
            @Server(
                    description = "PROD SERVER",
                    url = "http://norioportfolio.com:8080/api"
            )
        },
        security = @SecurityRequirement(
                name = "Security Token"
        )
)
@SecurityScheme(
        name = "Security Token",
        description = "Access Token For My API",
        type = SecuritySchemeType.HTTP,
        paramName = HttpHeaders.AUTHORIZATION,
        in = SecuritySchemeIn.HEADER,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class SwaggerConfig {
}

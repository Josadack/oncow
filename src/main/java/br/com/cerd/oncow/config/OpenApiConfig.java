package br.com.cerd.oncow.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "ONCOW API",
                version = "1.0",
                description = "API para gerenciamento de contribuições.",
                contact = @Contact(
                        name = "Josadaque Ferreira",
                        email = "josadaqueferreira@outlook.com"
                )
        )
)

public class OpenApiConfig {
}

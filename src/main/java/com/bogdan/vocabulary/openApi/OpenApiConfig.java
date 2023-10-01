package com.bogdan.vocabulary.openApi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Vocabulary project Api",
                description = "Vocabulary project", version = "0.1.2",
                contact = @Contact(
                        name = "Statyva Bogdan",
                        url = "https://github.com/bbogdasha"
                )
        )
)
public class OpenApiConfig {


}

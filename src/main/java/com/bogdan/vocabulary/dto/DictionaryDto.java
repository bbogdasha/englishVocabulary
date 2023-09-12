package com.bogdan.vocabulary.dto;

import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DictionaryDto {

    private Integer dictionaryId;

    @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$",
            message = "Invalid 'UUID' format.")
    private String nativeLanguageId;

    @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$",
            message = "Invalid 'UUID' format.")
    private String learnLanguageId;

    @Pattern(regexp = "^[a-zA-Z0-9]+('[a-zA-Z0-9])?[a-zA-Z0-9]{0,36}",
            message = "Invalid 'name' format.")
    private String dictionaryName;
}

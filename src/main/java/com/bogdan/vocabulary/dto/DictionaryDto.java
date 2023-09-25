package com.bogdan.vocabulary.dto;

import com.bogdan.vocabulary.model.Word;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DictionaryDto {

    @JsonView(View.SummaryDictionary.class)
    private Integer dictionaryId;

    @JsonView(View.SummaryDictionary.class)
    @NotBlank(message = "Native language id cannot be empty.")
    @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$",
            message = "Invalid 'UUID' format.")
    private String nativeLanguageId;

    @JsonView(View.SummaryDictionary.class)
    @NotBlank(message = "Learn language id cannot be empty.")
    @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$",
            message = "Invalid 'UUID' format.")
    private String learnLanguageId;

    @JsonView(View.SummaryDictionary.class)
    @NotBlank(message = "Dictionary name cannot be empty.")
    @Pattern(regexp = "^[a-zA-Z0-9]+('[a-zA-Z0-9])?[a-zA-Z0-9]{0,36}",
            message = "Invalid 'name' format.")
    private String dictionaryName;

    @JsonView(View.SummaryDictionary.class)
    private LocalDateTime createdAt;

    private List<Word> words;
}

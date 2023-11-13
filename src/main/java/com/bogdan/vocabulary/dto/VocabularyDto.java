package com.bogdan.vocabulary.dto;

import com.bogdan.vocabulary.model.Folder;
import com.bogdan.vocabulary.util.WhiteSpaceRemovalDeserializer;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VocabularyDto {

    @JsonView(View.SummaryVocabulary.class)
    private Integer vocabularyId;

    @JsonView(View.SummaryVocabulary.class)
    @NotBlank(message = "Native language id cannot be empty.")
    @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$",
            message = "Invalid 'UUID' format.")
    private String nativeLanguageId;

    @JsonView(View.SummaryVocabulary.class)
    @NotBlank(message = "Learn language id cannot be empty.")
    @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$",
            message = "Invalid 'UUID' format.")
    private String learnLanguageId;

    @JsonView(View.SummaryVocabulary.class)
    @NotBlank(message = "Vocabulary name cannot be empty.")
    @Pattern(regexp = "^[a-zA-Z0-9\" \"]+('[a-zA-Z0-9])?[a-zA-Z0-9\" \"]",
            message = "Invalid 'name' format.")
    @Size(max = 60, message = "Too many characters in 'vocabularyName'. Vocabulary name length: 1-120.")
    @JsonDeserialize(using = WhiteSpaceRemovalDeserializer.class)
    private String vocabularyName;

    @JsonView(View.SummaryVocabulary.class)
    @Size(max = 120, message = "Too many characters in 'description'. Description length: 1-120.")
    @JsonDeserialize(using = WhiteSpaceRemovalDeserializer.class)
    private String description;

    @JsonView(View.SummaryVocabulary.class)
    private LocalDateTime createdAt;

    private List<Folder> folders;
}

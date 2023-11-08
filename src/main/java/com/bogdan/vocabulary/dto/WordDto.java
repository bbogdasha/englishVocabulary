package com.bogdan.vocabulary.dto;

import com.bogdan.vocabulary.model.Vocabulary;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WordDto {

    private Integer wordId;

    @NotBlank(message = "Word cannot be empty.")
    @Size(max = 64, message = "Too many characters in 'word'. Word length: 1-64.")
    @Pattern(regexp = "^([^0-9]*)$",
            message = "Invalid 'word' format. Must not contain numbers.")
    private String word;

    @NotBlank(message = "Translation cannot be empty.")
    @Size(max = 64, message = "Too many characters in 'translation'. Translation length: 1-64.")
    @Pattern(regexp = "^([^0-9]*)$",
            message = "Invalid 'translation' format. Must not contain numbers.")
    private String translation;

    @Size(max = 255, message = "Too many characters in 'example'. Example length: 1-255.")
    private String example;

    private LocalDateTime createdAt;

    @JsonIgnore
    private Vocabulary vocabulary;
}

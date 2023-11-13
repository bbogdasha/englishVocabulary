package com.bogdan.vocabulary.dto;

import com.bogdan.vocabulary.model.Folder;
import com.bogdan.vocabulary.util.WhiteSpaceRemovalDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @JsonDeserialize(using = WhiteSpaceRemovalDeserializer.class)
    private String word;

    @NotBlank(message = "Translation cannot be empty.")
    @Size(max = 64, message = "Too many characters in 'translation'. Translation length: 1-64.")
    @Pattern(regexp = "^([^0-9]*)$",
            message = "Invalid 'translation' format. Must not contain numbers.")
    @JsonDeserialize(using = WhiteSpaceRemovalDeserializer.class)
    private String translation;

    @Size(max = 255, message = "Too many characters in 'example'. Example length: 1-255.")
    @JsonDeserialize(using = WhiteSpaceRemovalDeserializer.class)
    private String example;

    private LocalDateTime createdAt;

    @JsonIgnore
    private Folder folder;
}

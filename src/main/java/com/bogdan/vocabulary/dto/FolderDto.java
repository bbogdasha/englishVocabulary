package com.bogdan.vocabulary.dto;

import com.bogdan.vocabulary.model.Vocabulary;
import com.bogdan.vocabulary.model.Word;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
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
public class FolderDto {

    private Integer folderId;

    @NotBlank(message = "Folder name cannot be empty.")
    @Size(max = 64, message = "Too many characters in 'folder name'. Folder name: 1-64.")
    private String folderName;

    @Size(max = 120, message = "Too many characters in 'description'. Description length: 1-120.")
    private String description;

    private LocalDateTime createdAt;

    @JsonIgnore
    private Vocabulary vocabulary;

    @JsonIgnore
    private List<Word> words;

}

package com.bogdan.vocabulary.dto;

import com.bogdan.vocabulary.model.Dictionary;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class WordDto {

    private Integer wordId;

    private String word;

    private String translation;

    private String example;

    private LocalDateTime createdAt;

    @JsonIgnore
    private Dictionary dictionary;
}

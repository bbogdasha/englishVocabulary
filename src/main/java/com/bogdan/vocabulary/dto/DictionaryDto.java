package com.bogdan.vocabulary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DictionaryDto {

    private Integer dictionaryId;

    private Integer nativeLanguageId;

    private Integer learnLanguageId;

    private String dictionaryName;
}

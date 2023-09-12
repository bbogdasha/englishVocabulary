package com.bogdan.vocabulary.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "dictionaries")
public class Dictionary {

    @Id
    @SequenceGenerator(
            name = "dictionary_id_sequence",
            sequenceName = "dictionary_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "dictionary_id_sequence"
    )
    private Integer dictionaryId;

    private UUID nativeLanguageId;

    private UUID learnLanguageId;

    private String dictionaryName;
}

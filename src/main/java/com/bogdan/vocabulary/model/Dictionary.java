package com.bogdan.vocabulary.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Column(name = "native_language_id", nullable = false)
    private Integer nativeLanguageId;

    @Column(name = "learn_language_id", nullable = false)
    private Integer learnLanguageId;

    @Column(name = "dictionary_name", nullable = false)
    private String dictionaryName;
}

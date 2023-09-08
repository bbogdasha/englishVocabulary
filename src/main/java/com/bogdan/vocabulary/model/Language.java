package com.bogdan.vocabulary.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "languages")
public class Language {

    @Id
    @SequenceGenerator(
            name = "language_id_sequence",
            sequenceName = "language_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "language_id_sequence"
    )
    private Integer languageId;

    @Column(name = "language_name", nullable = false)
    private String languageName;
}

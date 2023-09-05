package com.bogdan.vocabulary.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "languages")
public class Language {

    @Id
    @SequenceGenerator(
            name = "language_id_sequence",
            sequenceName = "language_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "language_id_sequence"
    )
    private Integer language_id;
    private String language_name;
}

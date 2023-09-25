package com.bogdan.vocabulary.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
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

    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "dictionary", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Word> words;
}

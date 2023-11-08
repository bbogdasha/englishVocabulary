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
@Table(name = "vocabularies")
public class Vocabulary {

    @Id
    @SequenceGenerator(
            name = "vocabulary_id_sequence",
            sequenceName = "vocabulary_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "vocabulary_id_sequence"
    )
    private Integer vocabularyId;

    private UUID nativeLanguageId;

    private UUID learnLanguageId;

    private String vocabularyName;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "vocabulary", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Word> words;
}

package com.bogdan.vocabulary.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "folders")
public class Folder {

    @Id
    @SequenceGenerator(
            name = "folder_id_sequence",
            sequenceName = "folder_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "folder_id_sequence"
    )
    private Integer folderId;

    private String folderName;

    private String description;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vocabulary_id")
    @JsonIgnore
    private Vocabulary vocabulary;

    @OneToMany(mappedBy = "folder", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Word> words;


}

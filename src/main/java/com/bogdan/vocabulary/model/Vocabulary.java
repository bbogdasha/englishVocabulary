package com.bogdan.vocabulary.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "vocabularies")
public class Vocabulary extends DataAudit {

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

    private String description;

    @OneToMany(mappedBy = "vocabulary", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Folder> folders;

    public Integer getVocabularyId() {
        return vocabularyId;
    }

    public void setVocabularyId(Integer vocabularyId) {
        this.vocabularyId = vocabularyId;
    }

    public UUID getNativeLanguageId() {
        return nativeLanguageId;
    }

    public void setNativeLanguageId(UUID nativeLanguageId) {
        this.nativeLanguageId = nativeLanguageId;
    }

    public UUID getLearnLanguageId() {
        return learnLanguageId;
    }

    public void setLearnLanguageId(UUID learnLanguageId) {
        this.learnLanguageId = learnLanguageId;
    }

    public String getVocabularyName() {
        return vocabularyName;
    }

    public void setVocabularyName(String vocabularyName) {
        this.vocabularyName = vocabularyName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Folder> getFolders() {
        return folders;
    }

    public void setFolders(List<Folder> folders) {
        this.folders = folders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vocabulary that = (Vocabulary) o;
        return vocabularyId.equals(that.vocabularyId)
                && nativeLanguageId.equals(that.nativeLanguageId)
                && learnLanguageId.equals(that.learnLanguageId)
                && vocabularyName.equals(that.vocabularyName)
                && description.equals(that.description)
                && folders.equals(that.folders);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vocabularyId, nativeLanguageId, learnLanguageId,
                vocabularyName, description, folders);
    }
}

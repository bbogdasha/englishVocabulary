package com.bogdan.vocabulary.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "languages")
public class Language {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID languageId;

    private String languageName;

    public UUID getLanguageId() {
        return languageId;
    }

    public String getLanguageName() {
        return languageName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Language language = (Language) o;
        return languageId.equals(language.languageId)
                && languageName.equals(language.languageName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(languageId, languageName);
    }
}

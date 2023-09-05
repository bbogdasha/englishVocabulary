package com.bogdan.vocabulary.repository;

import com.bogdan.vocabulary.model.Language;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LanguageRepository extends JpaRepository<Language, Long> {
}

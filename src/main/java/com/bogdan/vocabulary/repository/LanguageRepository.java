package com.bogdan.vocabulary.repository;

import com.bogdan.vocabulary.model.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Long> {

    Language findByLanguageId(UUID id);
}

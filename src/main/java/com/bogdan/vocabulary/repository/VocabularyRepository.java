package com.bogdan.vocabulary.repository;

import com.bogdan.vocabulary.model.Vocabulary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VocabularyRepository extends JpaRepository<Vocabulary, Long> {

    @Query(value = """
            SELECT v.* FROM vocabularies v
            WHERE v.vocabulary_name ILIKE %:filter%
            """, nativeQuery = true)
    Page<Vocabulary> findAllVocabularies(Pageable wordPage, String filter);
}

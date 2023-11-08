package com.bogdan.vocabulary.repository;

import com.bogdan.vocabulary.model.Word;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WordRepository extends JpaRepository<Word, Long> {

    @Query(value = "select w.* from words w left join vocabularies v on w.vocabulary_id = v.vocabulary_id " +
            "where v.vocabulary_id = :vocabularyId and w.word_id = :wordId", nativeQuery = true)
    Optional<Word> findWordByVocabularyIdAndWordId(Long vocabularyId, Long wordId);

    @Query(value = "select w.* from words w left join vocabularies v on w.vocabulary_id = v.vocabulary_id " +
            "where v.vocabulary_id = :vocabularyId", nativeQuery = true)
    Page<Word> findAllWordsByVocabularyId(Long vocabularyId, Pageable wordPage);

    @Modifying
    @Query(value = "delete from words where word_id = :wordId", nativeQuery = true)
    void delete(Long wordId);
}

package com.bogdan.vocabulary.repository;

import com.bogdan.vocabulary.model.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WordRepository extends JpaRepository<Word, Long> {

    @Query(value = "select w.* from words w left join dictionaries d on w.dictionary_id = d.dictionary_id " +
            "where d.dictionary_id = :dictionaryId and w.word_id = :wordId", nativeQuery = true)
    Optional<Word> findWordByDictionaryIdAndWordId(Long dictionaryId, Long wordId);

    @Modifying
    @Query(value = "delete from words where word_id = :wordId", nativeQuery = true)
    void delete(Long wordId);
}

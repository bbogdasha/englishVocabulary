package com.bogdan.vocabulary.repository;

import com.bogdan.vocabulary.model.Word;
import com.bogdan.vocabulary.model.WordFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WordRepository extends JpaRepository<Word, Long>, JpaSpecificationExecutor<Word> {

    @Query(value = """
            SELECT w.* FROM words w
            JOIN folders f ON w.folder_id = f.folder_id
            JOIN vocabularies v ON f.vocabulary_id = v.vocabulary_id
            WHERE f.folder_id = :folderId AND v.vocabulary_id = :vocabularyId
            AND w.word ILIKE %:#{#filter.word}% AND w.translation ILIKE %:#{#filter.translation}%
            """, nativeQuery = true)
    Page<Word> findAllWordsByVocabularyIdAndFolderId(Long vocabularyId, Long folderId, Pageable wordPage,
                                                     @Param("filter") WordFilter filter);

//    @Modifying
//    @Query(value = "delete from words where word_id = :wordId", nativeQuery = true)
//    void delete(Long wordId);
}

package com.bogdan.vocabulary.repository;

import com.bogdan.vocabulary.model.Folder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FolderRepository extends JpaRepository<Folder, Long> {

    @Query(value = "select f.* from folders f where f.vocabulary_id = :vocabularyId and folder_name ilike %:filter%",
            nativeQuery = true)
    Page<Folder> findAllFoldersByVocabularyId(Long vocabularyId, Pageable wordPage, String filter);
}

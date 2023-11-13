package com.bogdan.vocabulary.service.word;

import com.bogdan.vocabulary.converter.FolderConverter;
import com.bogdan.vocabulary.converter.WordConverter;
import com.bogdan.vocabulary.dto.PageSettingsDto;
import com.bogdan.vocabulary.dto.WordDto;
import com.bogdan.vocabulary.exception.generalException.VocabularyNotFoundException;
import com.bogdan.vocabulary.exception.generalException.VocabularyValidationException;
import com.bogdan.vocabulary.model.*;
import com.bogdan.vocabulary.repository.WordRepository;
import com.bogdan.vocabulary.service.PageSettingsService;
import com.bogdan.vocabulary.service.folder.FolderServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class WordServiceImpl extends PageSettingsService implements WordService {

    private final WordRepository wordRepository;

    private final WordConverter wordConverter;

    private final FolderConverter folderConverter;

    private final FolderServiceImpl folderService;

    private static final String WORD_NOT_FOUND = "Word [id = %d] not found.";

    private static final List<String> SORT_COLUMN = Arrays.asList("created_at", "word", "translation");

    @Override
    @Transactional(readOnly = true)
    public PageSettingsDto<WordDto> getAllWordsByVocabularyAndFolder(Long vocabularyId, Long folderId,
                                                                     PageSettings pageSettings, WordFilter filter) {
        folderService.getFolderByVocabulary(vocabularyId, folderId);

        Sort wordSort = buildSort(SORT_COLUMN, pageSettings);
        Pageable pageRequest = PageRequest.of(pageSettings.getPage(), pageSettings.getElementPerPage(), wordSort);
        Page<Word> wordsPage = wordRepository.findAllWordsByVocabularyIdAndFolderId(vocabularyId, folderId, pageRequest, filter);

        return new PageSettingsDto<>(
                wordsPage.getContent().stream().map(wordConverter::convertToDto).toList(),
                wordsPage.getTotalElements(),
                wordsPage.getNumber(),
                wordsPage.getNumberOfElements()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public WordDto getWordByVocabularyAndFolder(Long vocabularyId, Long folderId, Long wordId) {
        Folder folder = folderConverter.convertToEntity(folderService.getFolderByVocabulary(vocabularyId, folderId));
        Optional<Word> optionalWord = folder.getWords().stream().filter(w -> w.getWordId() == wordId.intValue()).findFirst();

        if (optionalWord.isEmpty()) {
            throw new VocabularyNotFoundException(String.format(WORD_NOT_FOUND, wordId));
        }

        return wordConverter.convertToDto(optionalWord.get());
    }

    @Override
    @Transactional
    public List<WordDto> createWordsInFolder(Long vocabularyId, Long folderId, List<WordDto> wordsDto) {
        Folder folder = folderConverter.convertToEntity(folderService.getFolderByVocabulary(vocabularyId, folderId));

        List<Word> listSavedWords = new ArrayList<>();

        if (!wordsDto.isEmpty()) {
            for (WordDto wordDto : wordsDto) {
                Word word = wordConverter.convertToEntity(wordDto);
                word.setFolder(folder);
                folder.getWords().add(word);
                listSavedWords.add(word);
                wordRepository.save(word);
            }
        }

        return listSavedWords.isEmpty()
                ? new ArrayList<>()
                : listSavedWords.stream().map(wordConverter::convertToDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public WordDto patchWordByVocabularyAndFolder(Long vocabularyId, Long folderId, Long wordId, WordUpdateRequest request) {
        Folder folder = folderConverter.convertToEntity(folderService.getFolderByVocabulary(vocabularyId, folderId));
        Optional<Word> optionalWord = folder.getWords().stream().filter(w -> w.getWordId() == wordId.intValue()).findFirst();

        if (optionalWord.isEmpty()) {
            throw new VocabularyNotFoundException(String.format(WORD_NOT_FOUND, wordId));
        }

        Word word = optionalWord.get();
        boolean changes = false;

        if (request.word() != null && !request.word().equals(word.getWord())) {
            word.setWord(request.word());
            changes = true;
        }

        if (request.translation() != null && !request.translation().equals(word.getTranslation())) {
            word.setTranslation(request.translation());
            changes = true;
        }

        if (request.example() != null && !request.example().equals(word.getExample())) {
            word.setExample(request.example());
            changes = true;
        }

        if (!changes) {
            throw new VocabularyValidationException("No data changes found");
        }

        wordRepository.save(word);
        return wordConverter.convertToDto(word);
    }

    @Override
    @Transactional
    public void deleteWordInFolder(Long vocabularyId, Long folderId, Long wordId) {
        Folder folder = folderConverter.convertToEntity(folderService.getFolderByVocabulary(vocabularyId, folderId));
        Optional<Word> optionalWord = folder.getWords().stream().filter(w -> w.getWordId() == wordId.intValue()).findFirst();

        if (optionalWord.isEmpty()) {
            throw new VocabularyNotFoundException(String.format(WORD_NOT_FOUND, wordId));
        }

        folder.getWords().remove(optionalWord.get());
        wordRepository.deleteById(wordId);
    }

}

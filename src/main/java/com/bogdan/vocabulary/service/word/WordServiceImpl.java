package com.bogdan.vocabulary.service.word;

import com.bogdan.vocabulary.converter.DictionaryConverter;
import com.bogdan.vocabulary.converter.WordConverter;
import com.bogdan.vocabulary.dto.DictionaryDto;
import com.bogdan.vocabulary.dto.PageSettingsDto;
import com.bogdan.vocabulary.dto.WordDto;
import com.bogdan.vocabulary.exception.generalException.VocabularyNotFoundException;
import com.bogdan.vocabulary.model.Dictionary;
import com.bogdan.vocabulary.model.PageSettings;
import com.bogdan.vocabulary.model.Word;
import com.bogdan.vocabulary.repository.WordRepository;
import com.bogdan.vocabulary.service.dictionary.DictionaryServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class WordServiceImpl implements WordService {

    private final WordRepository wordRepository;

    private final WordConverter wordConverter;

    private final DictionaryConverter dictionaryConverter;

    private final DictionaryServiceImpl dictionaryService;

    private static final String WORD_NOT_FOUND = "Word with 'id = %d' not found.";

    @Override
    @Transactional(readOnly = true)
    public PageSettingsDto<WordDto> getAllWordsByDictionaryId(Long dictionaryId, PageSettings pageSettings) {

        dictionaryService.getDictionary(dictionaryId);

        Sort wordSort = pageSettings.buildSort();
        Pageable pageRequest = PageRequest.of(pageSettings.getPage(), pageSettings.getElementPerPage(), wordSort);
        Page<Word> wordsPage = wordRepository.findAllWordsByDictionaryId(dictionaryId, pageRequest);

        return new PageSettingsDto<>(
                wordsPage.getContent().stream().map(wordConverter::convertToDto).toList(),
                wordsPage.getTotalElements()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public WordDto getWordById(Long dictionaryId, Long wordId) {
        dictionaryService.getDictionary(dictionaryId);

        Optional<Word> optionalWord = wordRepository.findWordByDictionaryIdAndWordId(dictionaryId, wordId);

        if (optionalWord.isEmpty()) {
            throw new VocabularyNotFoundException(String.format(WORD_NOT_FOUND, wordId));
        }

        return wordConverter.convertToDto(optionalWord.get());
    }

    @Override
    @Transactional
    public List<WordDto> createWords(Long dictionaryId, List<WordDto> wordsDto) {
        DictionaryDto dictionaryDto = dictionaryService.getDictionary(dictionaryId);
        Dictionary dictionary = dictionaryConverter.convertToEntity(dictionaryDto);

        List<Word> listSavedWords = new ArrayList<>();

        if (!wordsDto.isEmpty()) {
            for (WordDto wordDto : wordsDto) {
                refactoringWord(wordDto);
                Word word = wordConverter.convertToEntity(wordDto);
                word.setCreatedAt(LocalDateTime.now());
                word.setDictionary(dictionary);
                listSavedWords.add(word);
            }

            dictionary.getWords().addAll(listSavedWords);
            wordRepository.saveAll(listSavedWords);
        }

        return listSavedWords.isEmpty()
                ? new ArrayList<>()
                : listSavedWords.stream().map(wordConverter::convertToDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public WordDto patchWord(Long dictionaryId, Long wordId, Map<String, Object> changes) {
        WordDto wordDto = getWordById(dictionaryId, wordId);

        refactoringWord(wordDto);

        Word patchedWord = wordConverter.convertToEntity(wordDto);

        changes.forEach((change, value) -> {
            switch (change) {
                case "word" -> patchedWord.setWord(value.toString());
                case "translation" -> patchedWord.setTranslation(value.toString());
                case "example" -> patchedWord.setExample(value.toString());
            }
        });

        wordRepository.save(patchedWord);

        return wordConverter.convertToDto(patchedWord);
    }

    @Override
    @Transactional
    public void deleteWord(Long dictionaryId, Long wordId) {
        DictionaryDto dictionaryDto = dictionaryService.getDictionary(dictionaryId);
        Dictionary dictionary = dictionaryConverter.convertToEntity(dictionaryDto);

        Optional<Word> optionalWord = wordRepository.findWordByDictionaryIdAndWordId(dictionaryId, wordId);

        if (optionalWord.isEmpty()) {
            throw new VocabularyNotFoundException(String.format(WORD_NOT_FOUND, wordId));
        }

        dictionary.getWords().remove(optionalWord.get());
        wordRepository.delete(wordId);
    }

    private void refactoringWord(WordDto wordDto) {
        wordDto.setWord(wordDto.getWord().trim());
        wordDto.setTranslation(wordDto.getTranslation().trim());
        wordDto.setExample(wordDto.getExample().trim());

        if (wordDto.getWord().contains("\n") || wordDto.getWord().contains("\r")) {
            wordDto.setWord(wordDto.getWord().replaceAll("\r\n|\r|\n", " "));
        }
    }
}

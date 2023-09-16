package com.bogdan.vocabulary.service.word;

import com.bogdan.vocabulary.converter.DictionaryConverter;
import com.bogdan.vocabulary.converter.WordConverter;
import com.bogdan.vocabulary.dto.DictionaryDto;
import com.bogdan.vocabulary.dto.WordDto;
import com.bogdan.vocabulary.exception.dict_lang.VocabularyNotFoundException;
import com.bogdan.vocabulary.model.Dictionary;
import com.bogdan.vocabulary.model.Word;
import com.bogdan.vocabulary.repository.WordRepository;
import com.bogdan.vocabulary.service.dictionary.DictionaryServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
    public DictionaryDto getAllWordsByDictionaryId(Long dictionaryId) {
        return dictionaryService.getDictionary(dictionaryId);
    }

    @Override
    public WordDto getWordById(Long dictionaryId, Long wordId) {
        dictionaryService.getDictionary(dictionaryId);

        Optional<Word> optionalWord = wordRepository.findWordByDictionaryIdAndWordId(dictionaryId, wordId);

        if (optionalWord.isEmpty()) {
            throw new VocabularyNotFoundException(String.format(WORD_NOT_FOUND, wordId));
        }

        return wordConverter.convertToDto(optionalWord.get());
    }

    @Override
    public List<WordDto> createWords(Long dictionaryId, List<WordDto> wordsDto) {
        DictionaryDto dictionaryDto = dictionaryService.getDictionary(dictionaryId);
        Dictionary dictionary = dictionaryConverter.convertToEntity(dictionaryDto);

        List<Word> listSavedWords = new ArrayList<>();

        if (!wordsDto.isEmpty()) {
            for (WordDto wordDto : wordsDto) {
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
    public WordDto patchWord(Long dictionaryId, Long wordId, Map<String, Object> changes) {
        return null;
    }

    @Override
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
}

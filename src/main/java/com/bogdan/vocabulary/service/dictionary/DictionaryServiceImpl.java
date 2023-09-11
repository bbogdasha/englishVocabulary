package com.bogdan.vocabulary.service.dictionary;

import com.bogdan.vocabulary.dto.DictionaryDto;
import com.bogdan.vocabulary.exception.dictionary.DictionaryValidationException;
import com.bogdan.vocabulary.exception.dictionary.DictionaryNotFoundException;
import com.bogdan.vocabulary.mapper.DictionaryMapper;
import com.bogdan.vocabulary.model.Dictionary;
import com.bogdan.vocabulary.repository.DictionaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Transactional
public class DictionaryServiceImpl implements DictionaryService {

    private final DictionaryRepository dictionaryRepository;

    @Autowired
    public DictionaryServiceImpl(DictionaryRepository dictionaryRepository) {
        this.dictionaryRepository = dictionaryRepository;
    }

    @Override
    public DictionaryDto createDictionary(DictionaryDto dictionaryDto) {
        boolean isValid = Pattern.matches(
                "^[a-zA-Z0-9]+('[a-zA-Z0-9])?[a-zA-Z0-9]{0,36}", dictionaryDto.getDictionaryName());

        if (!isValid) {
            throw new DictionaryValidationException("Invalid 'name' format.");
        }

        Dictionary dictionary = DictionaryMapper.mapToDictionary(dictionaryDto);
        Dictionary savedDictionary = dictionaryRepository.save(dictionary);
        return DictionaryMapper.mapToDictionaryDto(savedDictionary);
    }

    @Override
    public List<DictionaryDto> getAllDictionaries() {
        List<Dictionary> dictionaries = dictionaryRepository.findAll();
        return dictionaries.isEmpty()
                ? new ArrayList<>()
                : dictionaries.stream().map(DictionaryMapper::mapToDictionaryDto).collect(Collectors.toList());
    }

    @Override
    public DictionaryDto patchDictionary(Long id, Map<String, Object> changes) {
        Optional<Dictionary> optionalDictionary = dictionaryRepository.findById(id);

        if (optionalDictionary.isEmpty()) {
            throw new DictionaryNotFoundException("Dictionary with id: " + id + " not found.");
        }

        Dictionary savedDictionary = optionalDictionary.get();

        boolean isValid = Pattern.matches(
                "^[a-zA-Z0-9]+('[a-zA-Z0-9])?[a-zA-Z0-9]{0,36}", savedDictionary.getDictionaryName());

        if (!isValid) {
            throw new DictionaryValidationException("Invalid 'name' format.");
        }

        changes.forEach((change, value) -> {
            switch (change) {
                case "nativeLanguageId" -> savedDictionary.setNativeLanguageId((Integer) value);
                case "learnLanguageId" -> savedDictionary.setLearnLanguageId((Integer) value);
                case "dictionaryName" -> savedDictionary.setDictionaryName(value.toString());
            }
        });

        return DictionaryMapper.mapToDictionaryDto(savedDictionary);
    }

    @Override
    public void deleteDictionary(Long id) {
        Optional<Dictionary> optionalDictionary = dictionaryRepository.findById(id);

        if (optionalDictionary.isEmpty()) {
            throw new DictionaryNotFoundException("Dictionary with id: " + id + " not found.");
        }

        dictionaryRepository.deleteById(id);
    }
}

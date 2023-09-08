package com.bogdan.vocabulary.service.dictionary;

import com.bogdan.vocabulary.dto.DictionaryDto;
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

        Dictionary savedDictionary = optionalDictionary.orElse(null);

        if (savedDictionary == null) {
            return null;
            //todo
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
        dictionaryRepository.deleteById(id);
    }
}

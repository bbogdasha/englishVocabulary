package com.bogdan.vocabulary.converter;

import com.bogdan.vocabulary.dto.VocabularyDto;
import com.bogdan.vocabulary.model.Vocabulary;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class VocabularyConverter {

    private final ModelMapper modelMapper;

    public VocabularyConverter() {
        this.modelMapper = new ModelMapper();
    }

    public VocabularyDto convertToDto(Vocabulary vocabulary) {
        return modelMapper.map(vocabulary, VocabularyDto.class);
    }

    public Vocabulary convertToEntity(VocabularyDto vocabularyDto) {
        return modelMapper.map(vocabularyDto, Vocabulary.class);
    }

}

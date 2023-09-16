package com.bogdan.vocabulary.converter;

import com.bogdan.vocabulary.dto.DictionaryDto;
import com.bogdan.vocabulary.model.Dictionary;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class DictionaryConverter {

    private final ModelMapper modelMapper;

    public DictionaryConverter() {
        this.modelMapper = new ModelMapper();
    }

    public DictionaryDto convertToDto(Dictionary dictionary) {
        return modelMapper.map(dictionary, DictionaryDto.class);
    }

    public Dictionary convertToEntity(DictionaryDto dictionaryDto) {
        return modelMapper.map(dictionaryDto, Dictionary.class);
    }

}

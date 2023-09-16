package com.bogdan.vocabulary.converter;

import com.bogdan.vocabulary.dto.WordDto;
import com.bogdan.vocabulary.model.Word;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class WordConverter {

    private final ModelMapper modelMapper;

    public WordConverter() {
        this.modelMapper = new ModelMapper();
    }

    public WordDto convertToDto(Word word) {
        return modelMapper.map(word, WordDto.class);
    }

    public Word convertToEntity(WordDto wordDto) {
        return modelMapper.map(wordDto, Word.class);
    }
}

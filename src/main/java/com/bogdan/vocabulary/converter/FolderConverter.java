package com.bogdan.vocabulary.converter;

import com.bogdan.vocabulary.dto.FolderDto;
import com.bogdan.vocabulary.model.Folder;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class FolderConverter {

    private final ModelMapper modelMapper;

    public FolderConverter() {
        this.modelMapper = new ModelMapper();
    }

    public FolderDto convertToDto(Folder folder) {
        return modelMapper.map(folder, FolderDto.class);
    }

    public Folder convertToEntity(FolderDto folderDto) {
        return modelMapper.map(folderDto, Folder.class);
    }
}

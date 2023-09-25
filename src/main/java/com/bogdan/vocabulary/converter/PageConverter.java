package com.bogdan.vocabulary.converter;

import com.bogdan.vocabulary.dto.PageSettingsDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class PageConverter<T> {

    public PageSettingsDto<T> convertPageToPageDto(Page<T> page) {
        PageSettingsDto<T> pageDto = new PageSettingsDto<>();
        pageDto.setContent(page.getContent());
        pageDto.setTotalElements(page.getTotalElements());

        return pageDto;
    }

}

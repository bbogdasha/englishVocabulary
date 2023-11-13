package com.bogdan.vocabulary.dto;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PageSettingsDto<T> {

    @JsonView(View.SummaryVocabulary.class)
    private List<T> content;

    @JsonView(View.SummaryVocabulary.class)
    private Long totalElements;

    @JsonView(View.SummaryVocabulary.class)
    private int pageNumber;

    @JsonView(View.SummaryVocabulary.class)
    private int itemsOnPage;

    public PageSettingsDto(Page<T> page) {
        this.content = page.getContent();
        this.totalElements = page.getTotalElements();
        this.pageNumber = page.getNumber();
        this.itemsOnPage = page.getNumberOfElements();
    }
}

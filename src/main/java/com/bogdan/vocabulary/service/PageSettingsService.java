package com.bogdan.vocabulary.service;

import com.bogdan.vocabulary.exception.generalException.VocabularyValidationException;
import com.bogdan.vocabulary.model.PageSettings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Slf4j
@Service
public abstract class PageSettingsService {

    private static final String SORT_FILTER_NOT_FOUND = "Column [%s] not found.";

    public Sort buildSort(List<String> columns, PageSettings pageSettings) {

        if (!columns.contains(pageSettings.getSortField())) {
            throw new VocabularyValidationException(String.format(SORT_FILTER_NOT_FOUND, pageSettings.getSortField()));
        }

        pageSettings.setSortOrder(pageSettings.getSortOrder().toLowerCase(Locale.ROOT));

        switch (pageSettings.getSortOrder()) {
            case "asc" -> {
                return Sort.by(pageSettings.getSortField()).ascending();
            }
            case "desc" -> {
                return Sort.by(pageSettings.getSortField()).descending();
            }
            default -> {
                log.warn("Invalid direction provided in PageSettings, using ascending direction as default value");
                return Sort.by(pageSettings.getSortField()).ascending();
            }
        }
    }

}

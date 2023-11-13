package com.bogdan.vocabulary.model;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;

import java.util.Locale;

@Slf4j
@Data
public class PageSettings {

    private Integer page = 0;

    private int elementPerPage = 10;

    private String sortOrder = "asc";

    private String sortField = "created_at";

    public Sort buildSort() {
        sortOrder = sortOrder.toLowerCase(Locale.ROOT);
        switch (sortOrder) {
            case "asc" -> {
                return Sort.by(sortField).ascending();
            }
            case "desc" -> {
                return Sort.by(sortField).descending();
            }
            default -> {
                log.warn("Invalid direction provided in PageSettings, using ascending direction as default value");
                return Sort.by(sortField).ascending();
            }
        }
    }
}

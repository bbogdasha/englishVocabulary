package com.bogdan.vocabulary.model;

import lombok.Data;
import org.springframework.data.domain.Sort;

import java.util.Locale;

@Data
public class PageSettings {

    private Integer page = 0;

    private int elementPerPage = 10;

    private String sortOrder = "asc";

    private String sortField = "created_at";

    public Sort buildSort() {
        sortOrder = sortOrder.toLowerCase(Locale.ROOT);
        return switch (sortOrder) {
            case "desc" -> Sort.by(sortField).descending();
            default -> Sort.by(sortField).ascending();
        };
    }
}

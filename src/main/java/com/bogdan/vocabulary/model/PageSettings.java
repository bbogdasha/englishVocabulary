package com.bogdan.vocabulary.model;

import lombok.Data;
import org.springframework.data.domain.Sort;

import java.util.Locale;

@Data
public class PageSettings {

    private Integer page = 0;

    private int elementPerPage = 3;

    private String direction = "asc";

    private String key = "createdAt";

    public Sort buildSort() {
        direction = direction.toLowerCase(Locale.ROOT);
        return switch (direction) {
            case "dsc" -> Sort.by(key).descending();
            default -> Sort.by(key).ascending();
        };
    }
}

package com.bogdan.vocabulary.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
public class PageSettings {

    private Integer page = 0;

    private int elementPerPage = 10;

    private String sortOrder = "asc";

    private String sortField = "created_at";

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public int getElementPerPage() {
        return elementPerPage;
    }

    public void setElementPerPage(int elementPerPage) {
        this.elementPerPage = elementPerPage;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PageSettings that = (PageSettings) o;
        return elementPerPage == that.elementPerPage
                && page.equals(that.page)
                && sortOrder.equals(that.sortOrder)
                && sortField.equals(that.sortField);
    }

    @Override
    public int hashCode() {
        return Objects.hash(page, elementPerPage, sortOrder, sortField);
    }
}

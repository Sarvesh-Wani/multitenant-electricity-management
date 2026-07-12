package com.coditas.multitenantelectricitymanagement.dto;

import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaginationResponse<T> {

    private List<T> content;

    private long totalElements;

    private int totalPages;

    private int currentPage;

    private int pageSize;

    private boolean first;

    private boolean last;

    public PaginationResponse(Page<T> page) {
        this.content = page.getContent();
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();
        this.currentPage = page.getNumber();
        this.pageSize = page.getSize();
        this.first = page.isFirst();
        this.last = page.isLast();
    }
}

package com.example.blogsearch.blog.domain.response;

import lombok.Builder;

@Builder
public record BlogPage(Integer totalCount,
                       Boolean isFirstPage,
                       Boolean isLastPage,
                       Integer requestPage,
                       Integer requestSize) {
}

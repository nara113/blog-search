package com.example.blogsearch.keyword.domain;

import com.example.blogsearch.keyword.entity.KeywordCount;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "인기 검색어")
public record PopularKeyword(
        @Schema(description = "검색어") String keyword,
        @Schema(description = "검색된 횟수") Long searchCount) {
    public static PopularKeyword of(KeywordCount keywordCount) {
        return new PopularKeyword(keywordCount.getKeyword(), keywordCount.getCount());
    }
}

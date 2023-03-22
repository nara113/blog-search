package com.example.blogsearch.blog.domain.client.response.kakao;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Meta(@JsonProperty("total_count") Integer totalCount,
                   @JsonProperty("pageable_count") Integer pageableCount,
                   @JsonProperty("is_end") Boolean isEnd) {
}

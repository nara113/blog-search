package com.example.blogsearch.blog.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.ToString;

@Schema(description = "블로그 검색 요청")
public record BlogSearchRequest(
        @NotBlank @Schema(description = "검색 질의어") String keyword,
        @Schema(description = "결과 문서 정렬 방식", defaultValue = "accuracy") PageSort sort,
        @Min(1) @Max(50) @Schema(description = "결과 페이지 번호", defaultValue = "1") Integer page,
        @Min(1) @Max(50) @Schema(description = "한 페이지에 보여질 문서 수", defaultValue = "10") Integer size) {
}

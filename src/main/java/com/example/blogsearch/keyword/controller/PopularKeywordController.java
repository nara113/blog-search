package com.example.blogapi.keyword.controller;

import com.example.blogapi.keyword.domain.PopularKeyword;
import com.example.blogapi.keyword.service.PopularKeywordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Tag(name = "popular-keywords", description = "popular keywords API")
@RequiredArgsConstructor
@RequestMapping("/api/popular-keywords")
@RestController
public class PopularKeywordController {
    private final PopularKeywordService popularKeywordService;

    @Operation(summary = "인기 검색어 목록 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청 성공")})
    @GetMapping
    public List<PopularKeyword> getPopularSearchKeyword() {
        return popularKeywordService.getTop10PopularKeywords();
    }
}

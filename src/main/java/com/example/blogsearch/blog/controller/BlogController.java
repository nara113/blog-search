package com.example.blogsearch.blog.controller;

import com.example.blogsearch.blog.domain.request.BlogSearchRequest;
import com.example.blogsearch.blog.domain.response.BlogSearchResponse;
import com.example.blogsearch.blog.service.BlogService;
import com.example.blogsearch.common.response.ApiErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "blog", description = "blog API")
@RequiredArgsConstructor
@RequestMapping("/api/blog")
@RestController
public class BlogController {

    private final BlogService blogService;

    @Operation(summary = "블로그 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "503", description = "서버 에러",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))})
    @GetMapping
    public BlogSearchResponse search(@Validated BlogSearchRequest blogSearchRequest) {
        return blogService.search(blogSearchRequest);
    }
}

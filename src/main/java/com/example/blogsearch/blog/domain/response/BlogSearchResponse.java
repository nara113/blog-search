package com.example.blogapi.blog.domain.response;

import com.example.blogapi.blog.domain.client.response.kakao.KakaoApiResponse;
import com.example.blogapi.blog.domain.client.response.naver.NaverApiResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;

import java.util.List;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Schema(description = "블로그 검색 결과")
public class BlogSearchResponse {
    private static final int FIRST_PAGE = 1;
    private static final int DEFAULT_PAGE = 1;
    private static final int DEFAULT_SIZE = 10;
    private static final int MAX_PAGE_SIZE = 50;

    @Schema(description = "페이지 정보")
    private final BlogPage blogPage;

    @Schema(description = "블로그 컨텐츠")
    private final List<BlogContent> contents;

    public static BlogSearchResponse of(
            KakaoApiResponse kakaoApiResponse,
            Integer page,
            Integer size) {
        return new BlogSearchResponse(
                BlogPage.builder()
                        .totalCount(kakaoApiResponse.getMeta().totalCount())
                        .isFirstPage(isFirstPage(page))
                        .isLastPage(kakaoApiResponse.getMeta().isEnd())
                        .requestPage(page == null ? DEFAULT_PAGE : page)
                        .requestSize(size == null ? DEFAULT_SIZE : size)
                        .build(),
                kakaoApiResponse.getDocuments()
                        .stream()
                        .map(BlogContent::of)
                        .toList()
        );
    }

    public static BlogSearchResponse of(NaverApiResponse naverApiResponse) {
        return new BlogSearchResponse(
                BlogPage.builder()
                        .totalCount(naverApiResponse.total())
                        .isFirstPage(isFirstPage(naverApiResponse.start()))
                        .isLastPage(isLastPage(naverApiResponse))
                        .requestPage(naverApiResponse.start())
                        .requestSize(naverApiResponse.display())
                        .build(),
                naverApiResponse.items()
                        .stream()
                        .map(BlogContent::of)
                        .toList()
        );
    }

    private static boolean isFirstPage(Integer page) {
        return page == null || page.equals(FIRST_PAGE);
    }

    private static boolean isLastPage(NaverApiResponse naverApiResponse) {
        if (!ObjectUtils.allNotNull(
                naverApiResponse.total(),
                naverApiResponse.display(),
                naverApiResponse.start())) {
            return false;
        }

        int lastPage = (naverApiResponse.total() / naverApiResponse.display())
                + (naverApiResponse.total() % naverApiResponse.display() != 0 ? 1 : 0);

        return naverApiResponse.start() == MAX_PAGE_SIZE || naverApiResponse.start() >= lastPage;
    }
}

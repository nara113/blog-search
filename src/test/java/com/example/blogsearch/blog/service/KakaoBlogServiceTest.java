package com.example.blogapi.blog.service;

import com.example.blogapi.blog.client.KakaoBlogClient;
import com.example.blogapi.blog.domain.client.response.kakao.KakaoApiResponse;
import com.example.blogapi.blog.domain.client.response.kakao.Meta;
import com.example.blogapi.blog.domain.request.BlogSearchRequest;
import feign.RetryableException;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.stream.IntStream;

import static org.mockito.BDDMockito.*;

@TestPropertySource(properties = {
        "resilience4j.circuitbreaker.configs.default.failureRateThreshold=50",
        "resilience4j.circuitbreaker.configs.default.minimumNumberOfCalls=10",
        "resilience4j.retry.configs.default.maxAttempts=3"
})
@SpringBootTest
class KakaoBlogServiceTest {
    private static final int TOTAL_CALLS = 20;
    private static final int MINIMUM_NUMBER_OF_CALLS = 10;
    private static final int RETRY_COUNT = 3;

    @Autowired
    private KakaoBlogService kakaoBlogService;

    @MockBean
    private KakaoBlogClient kakaoBlogClient;
    @MockBean
    private NaverBlogService naverBlogService;

    @DisplayName("api 20회 호출 성공")
    @Test
    void success() {
        //given
        given(kakaoBlogClient.search(any(), any(), any(), any()))
                .willReturn(getSuccessKakaoApiResponse());

        BlogSearchRequest request = getBlogSearchRequest();

        //when
        IntStream.rangeClosed(1, TOTAL_CALLS)
                .forEach(i -> kakaoBlogService.search(request));

        //then
        then(kakaoBlogClient).should(times(TOTAL_CALLS))
                .search(any(), any(), any(), any());

        then(naverBlogService).should(never())
                .search(request);
    }

    @DisplayName("api 20회 호출 실패 - 10번 3번씩 재시도(30회) 이후 서킷 오픈")
    @Test
    void fail() {
        //given
        given(kakaoBlogClient.search(any(), any(), any(), any()))
                .willThrow(RetryableException.class);

        BlogSearchRequest request = getBlogSearchRequest();

        int apiCallsAfterCircuitOpened = TOTAL_CALLS - MINIMUM_NUMBER_OF_CALLS;

        //when
        IntStream.rangeClosed(1, TOTAL_CALLS)
                .forEach(i -> kakaoBlogService.search(request));

        //then
        then(kakaoBlogClient).should(times(MINIMUM_NUMBER_OF_CALLS * RETRY_COUNT))
                .search(any(), any(), any(), any());

        then(naverBlogService).should(times(MINIMUM_NUMBER_OF_CALLS + apiCallsAfterCircuitOpened))
                .search(request);
    }

    @DisplayName("api 4번 호출 성공 - 10번 3번씩 재시도(30회) 이후 서킷 오픈")
    @Test
    void fail2() {
        //given
        KakaoApiResponse successResponse = getSuccessKakaoApiResponse();

        given(kakaoBlogClient.search(any(), any(), any(), any()))
                .willReturn(successResponse, successResponse, successResponse, successResponse)
                .willThrow(RetryableException.class);

        int successCount = 4;
        int failCount = MINIMUM_NUMBER_OF_CALLS - successCount;
        int apiCallsAfterCircuitOpened = TOTAL_CALLS - MINIMUM_NUMBER_OF_CALLS;

        //when
        IntStream.rangeClosed(1, TOTAL_CALLS)
                .forEach(i -> kakaoBlogService.search(getBlogSearchRequest()));

        //then
        then(kakaoBlogClient).should(times(failCount * RETRY_COUNT + successCount))
                .search(any(), any(), any(), any());

        then(naverBlogService).should(times(failCount + apiCallsAfterCircuitOpened))
                .search(any());
    }

    @NotNull
    private BlogSearchRequest getBlogSearchRequest() {
        return new BlogSearchRequest("test", null, null, null);
    }

    @NotNull
    private KakaoApiResponse getSuccessKakaoApiResponse() {
        return new KakaoApiResponse(new Meta(null, null, true), new ArrayList<>());
    }

}
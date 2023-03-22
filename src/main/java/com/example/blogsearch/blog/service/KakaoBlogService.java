package com.example.blogapi.blog.service;

import com.example.blogapi.blog.client.KakaoBlogClient;
import com.example.blogapi.blog.domain.client.response.kakao.KakaoApiResponse;
import com.example.blogapi.blog.domain.request.BlogSearchRequest;
import com.example.blogapi.blog.domain.response.BlogSearchResponse;
import feign.RetryableException;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@RequiredArgsConstructor
@Service
public class KakaoBlogService {
    private final KakaoBlogClient kakaoBlogClient;
    private final NaverBlogService naverBlogService;

    @CircuitBreaker(name = "kakaoCircuitBreaker", fallbackMethod = "fallback")
    @Retry(name = "kakaoCircuitBreaker")
    public BlogSearchResponse search(BlogSearchRequest blogSearchRequest) {
        KakaoApiResponse kakaoApiResponse = kakaoBlogClient.search(
                URLEncoder.encode(blogSearchRequest.keyword(), StandardCharsets.UTF_8),
                blogSearchRequest.page(),
                blogSearchRequest.size(),
                blogSearchRequest.sort() == null ? null : blogSearchRequest.sort().getKakaoSort());

        return BlogSearchResponse.of(kakaoApiResponse, blogSearchRequest.page(), blogSearchRequest.size());
    }

    private BlogSearchResponse fallback(BlogSearchRequest blogSearchRequest, RetryableException exception) {
        log.error("kakao api call error.", exception);

        return naverBlogService.search(blogSearchRequest);
    }

    private BlogSearchResponse fallback(BlogSearchRequest blogSearchRequest, CallNotPermittedException exception) {
        log.error("kakao api circuit opened.", exception);

        return naverBlogService.search(blogSearchRequest);
    }

}

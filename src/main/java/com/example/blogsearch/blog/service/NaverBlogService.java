package com.example.blogapi.blog.service;

import com.example.blogapi.blog.client.NaverBlogClient;
import com.example.blogapi.blog.domain.client.response.naver.NaverApiResponse;
import com.example.blogapi.blog.domain.request.BlogSearchRequest;
import com.example.blogapi.blog.domain.response.BlogSearchResponse;
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
public class NaverBlogService {
    private final NaverBlogClient naverBlogClient;

    @CircuitBreaker(name = "naverCircuitBreaker")
    @Retry(name = "naverCircuitBreaker")
    public BlogSearchResponse search(BlogSearchRequest blogSearchRequest) {
        NaverApiResponse naverApiResponse = naverBlogClient.search(
                URLEncoder.encode(blogSearchRequest.keyword(), StandardCharsets.UTF_8),
                blogSearchRequest.page(),
                blogSearchRequest.size(),
                blogSearchRequest.sort() == null ? null : blogSearchRequest.sort().getNaverSort());

        return BlogSearchResponse.of(naverApiResponse);
    }
}

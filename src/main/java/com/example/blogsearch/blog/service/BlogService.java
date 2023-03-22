package com.example.blogsearch.blog.service;

import com.example.blogsearch.blog.domain.request.BlogSearchRequest;
import com.example.blogsearch.blog.domain.response.BlogSearchResponse;
import com.example.blogsearch.keyword.event.SearchedKeywordEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class BlogService {
    private final KakaoBlogService kakaoBlogService;
    private final ApplicationEventPublisher applicationEventPublisher;

    public BlogSearchResponse search(BlogSearchRequest blogSearchRequest) {
        applicationEventPublisher.publishEvent(new SearchedKeywordEvent(blogSearchRequest.keyword()));

        return kakaoBlogService.search(blogSearchRequest);
    }

}

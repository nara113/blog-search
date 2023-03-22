package com.example.blogsearch.blog.domain.client.response.kakao;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class KakaoApiResponse {
    private final Meta meta;
    private final List<KakaoBlogDocument> documents;
}

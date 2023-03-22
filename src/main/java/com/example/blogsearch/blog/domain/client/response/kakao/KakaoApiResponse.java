package com.example.blogapi.blog.domain.client.response.kakao;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class KakaoApiResponse {
    private final Meta meta;
    private final List<KakaoBlogDocument> documents;
}

package com.example.blogapi.blog.domain.response;

import com.example.blogapi.blog.domain.client.response.kakao.KakaoBlogDocument;
import com.example.blogapi.blog.domain.client.response.naver.NaverBlogDocument;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class BlogContent {
    private final String title;
    private final String contents;
    private final String url;
    private final String blogname;
    private final String thumbnail;
    private final LocalDateTime datetime;

    public static BlogContent of(KakaoBlogDocument kakaoBlogDocument) {
        return new BlogContent(kakaoBlogDocument.getTitle(),
                kakaoBlogDocument.getContents(),
                kakaoBlogDocument.getUrl(),
                kakaoBlogDocument.getBlogname(),
                kakaoBlogDocument.getThumbnail(),
                kakaoBlogDocument.getDatetime());
    }

    public static BlogContent of(NaverBlogDocument naverBlogDocument) {
        return new BlogContent(naverBlogDocument.getTitle(),
                naverBlogDocument.getDescription(),
                naverBlogDocument.getBloggerlink(),
                naverBlogDocument.getBloggername(),
                null,
                naverBlogDocument.getPostdate().atStartOfDay());
    }
}

package com.example.blogapi.blog.domain.client.response.kakao;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@RequiredArgsConstructor
public class KakaoBlogDocument {
    private final String title;
    private final String contents;
    private final String url;
    private final String blogname;
    private final String thumbnail;
    private final String datetime;

    public LocalDateTime getDatetime() {
        return LocalDateTime.parse(datetime, DateTimeFormatter.ISO_DATE_TIME);
    }
}

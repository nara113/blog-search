package com.example.blogsearch.blog.domain.client.response.naver;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Getter
@RequiredArgsConstructor
public class NaverBlogDocument {
    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    private final String title;
    private final String description;
    private final String bloggerlink;
    private final String bloggername;
    private final String postdate;


    public LocalDate getPostdate() {
        return LocalDate.parse(postdate, formatter);
    }
}

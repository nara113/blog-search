package com.example.blogapi.blog.config.converter;

import com.example.blogapi.blog.domain.request.PageSort;
import org.springframework.core.convert.converter.Converter;

public class PageSortRequestConverter implements Converter<String, PageSort> {
    @Override
    public PageSort convert(String kakaoSort) {
        return PageSort.of(kakaoSort);
    }
}

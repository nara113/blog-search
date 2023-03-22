package com.example.blogsearch.blog.config.converter;

import com.example.blogsearch.blog.domain.request.PageSort;
import org.springframework.core.convert.converter.Converter;

public class PageSortRequestConverter implements Converter<String, PageSort> {
    @Override
    public PageSort convert(String kakaoSort) {
        return PageSort.of(kakaoSort);
    }
}

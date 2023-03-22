package com.example.blogsearch.blog.domain.client.response.naver;

import java.util.List;

public record NaverApiResponse(String lastBuildDate,
                               Integer total,
                               Integer start,
                               Integer display,
                               List<NaverBlogDocument> items) {
}

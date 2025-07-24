package com.juanba.clone_google.dto;

import com.juanba.clone_google.entities.WebPage;

public record WebPageDto(
        String url,
        String title,
        String description
) {
    public WebPageDto(WebPage webPage) {
        this(
                webPage.getUrl(),
                webPage.getTitle(),
                webPage.getDescription()
        );
    }
}

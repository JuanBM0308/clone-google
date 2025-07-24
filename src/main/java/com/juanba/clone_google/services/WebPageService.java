package com.juanba.clone_google.services;

import com.juanba.clone_google.dto.WebPageDto;
import com.juanba.clone_google.entities.WebPage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface WebPageService {
    Page<WebPageDto> listResults(Pageable pageable, String textSearch);
    void save(WebPage webPage);
    boolean exist(String link);
    List<WebPage> getLinksToIndex();
}

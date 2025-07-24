package com.juanba.clone_google.services;

import com.juanba.clone_google.dto.WebPageDto;
import com.juanba.clone_google.entities.WebPage;
import com.juanba.clone_google.repositories.WebPageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WebPageServiceImpl implements WebPageService {

    private final WebPageRepository webPageRepository;

    @Override
    public Page<WebPageDto> listResults(Pageable pageable, String textSearch) {
        return webPageRepository.findAllByIsActiveTrueAndDescriptionContainingIgnoreCase(textSearch, pageable)
                .map(WebPageDto::new);
    }

    @Override
    public void save(WebPage webPage) {
        webPageRepository.save(webPage);
    }

    @Override
    public boolean exist(String link) {
        return webPageRepository.findByUrl(link).isPresent();
    }

    @Override
    public List<WebPage> getLinksToIndex() {
        return webPageRepository.findTop50ByTitleNullAndDescriptionNull();
    }
}

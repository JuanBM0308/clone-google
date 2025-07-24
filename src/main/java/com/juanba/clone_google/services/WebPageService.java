package com.juanba.clone_google.services;

import com.juanba.clone_google.dto.WebPageDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WebPageService {
    Page<WebPageDto> listResults(Pageable pageable, String textSearch);
}

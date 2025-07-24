package com.juanba.clone_google.controllers;

import com.juanba.clone_google.dto.WebPageDto;
import com.juanba.clone_google.services.WebPageServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/clone-google/api/v1/search")
public class SearchController {

    private final WebPageServiceImpl webPageService;

    public SearchController(WebPageServiceImpl webPageService) {
        this.webPageService = webPageService;
    }

    @GetMapping
    public ResponseEntity<Page<WebPageDto>> search(@PageableDefault(size = 50) Pageable pageable, @RequestParam Map<String,String> params) {
        String query = params.get("query");
        return ResponseEntity.ok(webPageService.listResults(pageable, query));
    }
}

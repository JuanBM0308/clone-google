package com.juanba.clone_google.repositories;

import com.juanba.clone_google.entities.WebPage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WebPageRepository extends JpaRepository<WebPage, Long> {
    Page<WebPage> findAllByIsActiveTrueAndDescriptionContainingIgnoreCase(String description, Pageable pageable);
    Optional<WebPage> findByUrl(String url);
    List<WebPage> findTop50ByTitleNullAndDescriptionNull();
}

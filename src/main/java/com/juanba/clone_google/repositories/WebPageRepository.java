package com.juanba.clone_google.repositories;

import com.juanba.clone_google.entities.WebPage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebPageRepository extends JpaRepository<WebPage, Long> {
    Page<WebPage> findAllByIsActiveTrueAndDescriptionContainingIgnoreCase(String description, Pageable pageable);
}

package com.example.shorturl.repository;

import com.example.shorturl.model.ShortUrl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShortUrlRepository extends JpaRepository<ShortUrl, Long> {

    /** Look up only active mappings by alias */
    Optional<ShortUrl> findByShortCodeAndActiveTrue(String shortCode);

    /** Check if an alias already exists */
    boolean existsByShortCode(String shortCode);

    /** Page through all mappings owned by a specific user */
    Page<ShortUrl> findAllByOwnerId(Long ownerId, Pageable pageable);
}

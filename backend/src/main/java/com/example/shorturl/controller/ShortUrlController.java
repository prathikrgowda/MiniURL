// src/main/java/com/example/shorturl/controller/ShortUrlController.java
package com.example.shorturl.controller;

import com.example.shorturl.dto.CreateLinkDto;
import com.example.shorturl.dto.UpdateLinkDto;
import com.example.shorturl.dto.AnalyticsDto;
import com.example.shorturl.model.ShortUrl;
import com.example.shorturl.service.ShortUrlService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;    // ← updated to Jakarta
import jakarta.servlet.http.HttpServletResponse;   // ← updated to Jakarta

import java.io.IOException;
import java.net.URI;
import java.security.Principal;
import java.time.LocalDate;
import java.util.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class ShortUrlController {

    @Autowired
    private ShortUrlService service;

    /** 
     * Create a new short link 
     * POST /api/links
     */
    @PostMapping("/api/links")
    public ResponseEntity<ShortUrl> createLink(
            @Valid @RequestBody CreateLinkDto dto,
            Principal principal
    ) {
        Long ownerId = Long.parseLong(principal.getName());
        ShortUrl su = service.createMapping(
                dto.getOriginalUrl(),
                dto.getAlias(),
                dto.getExpiresAt(),
                dto.getMaxClicks(),
                ownerId
        );
        return ResponseEntity
                .created(URI.create("/api/links/" + su.getId()))
                .body(su);
    }

    /**
     * List all links for the authenticated user (paginated)
     * GET /api/links
     */
    @GetMapping("/api/links")
    public ResponseEntity<Page<ShortUrl>> listLinks(
            @PageableDefault(size = 20) Pageable pageable,
            Principal principal
    ) {
        Long ownerId = Long.parseLong(principal.getName());
        Page<ShortUrl> page = service.listMappings(ownerId, pageable);
        return ResponseEntity.ok(page);
    }

    /**
     * Update an existing link
     * PUT /api/links/{id}
     */
    @PutMapping("/api/links/{id}")
    public ResponseEntity<ShortUrl> updateLink(
            @PathVariable Long id,
            @Valid @RequestBody UpdateLinkDto dto,
            Principal principal
    ) {
        Long ownerId = Long.parseLong(principal.getName());
        ShortUrl su = service.updateMapping(
                id,
                dto.getOriginalUrl(),
                dto.getExpiresAt(),
                dto.getMaxClicks(),
                dto.getActive(),
                ownerId
        );
        return ResponseEntity.ok(su);
    }

    /**
     * Deactivate (soft-delete) a link
     * DELETE /api/links/{id}
     */
    @DeleteMapping("/api/links/{id}")
    public ResponseEntity<Void> deactivateLink(
            @PathVariable Long id,
            Principal principal
    ) {
        Long ownerId = Long.parseLong(principal.getName());
        service.deactivateMapping(id, ownerId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Fetch analytics for a link
     * GET /api/links/{id}/analytics
     */
    @GetMapping("/api/links/{id}/analytics")
    public ResponseEntity<AnalyticsDto> getAnalytics(
            @PathVariable Long id,
            Principal principal
    ) {
        Long ownerId = Long.parseLong(principal.getName());
        Map<String, Object> data = service.getAnalytics(id, ownerId);

        AnalyticsDto dto = new AnalyticsDto();
        dto.setTotalClicks((Long) data.get("totalClicks"));
        dto.setUniqueClicks((Long) data.get("uniqueClicks"));

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> raw = (List<Map<String, Object>>) data.get("clicksOverTime");
        List<AnalyticsDto.DailyClick> clicks = new ArrayList<>();
        for (Map<String, Object> entry : raw) {
            AnalyticsDto.DailyClick dc = new AnalyticsDto.DailyClick();
            dc.setDate((LocalDate) entry.get("date"));
            dc.setCount((Long) entry.get("count"));
            clicks.add(dc);
        }
        dto.setClicksOverTime(clicks);

        return ResponseEntity.ok(dto);
    }

    /**
     * Public redirect endpoint
     * GET /{alias}
     */
    @GetMapping("/{alias}")
    public void redirect(
            @PathVariable String alias,
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        ShortUrl su = service.handleRedirect(alias, request);
        response.sendRedirect(su.getOriginalUrl());
    }
}

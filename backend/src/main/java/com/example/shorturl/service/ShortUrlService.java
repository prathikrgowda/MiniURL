package com.example.shorturl.service;

import com.example.shorturl.model.ShortUrl;
import com.example.shorturl.model.ClickEvent;
import com.example.shorturl.repository.ShortUrlRepository;
import com.example.shorturl.repository.ClickEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import jakarta.servlet.http.HttpServletRequest;  // ← updated import

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ShortUrlService {

    @Autowired
    private ShortUrlRepository shortUrlRepository;

    @Autowired
    private ClickEventRepository clickEventRepository;

    /**
     * Create a new mapping, checking alias collisions if provided.
     */
    public ShortUrl createMapping(String originalUrl,
                                  String alias,
                                  LocalDateTime expiresAt,
                                  Integer maxClicks,
                                  Long ownerId) {
        if (alias != null && !alias.isBlank()) {
            if (shortUrlRepository.existsByShortCode(alias)) {
                throw new IllegalArgumentException("Alias already in use");
            }
        } else {
            alias = generateShortCode();
            while (shortUrlRepository.existsByShortCode(alias)) {
                alias = generateShortCode();
            }
        }

        ShortUrl su = new ShortUrl();
        su.setOriginalUrl(originalUrl);
        su.setShortCode(alias);
        su.setCreatedAt(LocalDateTime.now());
        su.setExpiresAt(expiresAt);
        su.setMaxClicks(maxClicks);
        su.setOwnerId(ownerId);
        su.setClickCount(0);
        su.setActive(true);

        return shortUrlRepository.save(su);
    }

    /**
     * Handle a redirect request:
     * - lookup active mapping
     * - enforce TTL and max-clicks
     * - increment clickCount
     * - record a ClickEvent
     * - return the mapping (for controller to redirect)
     */
    public ShortUrl handleRedirect(String alias, HttpServletRequest request) {
        ShortUrl su = shortUrlRepository.findByShortCodeAndActiveTrue(alias)
                .orElseThrow(() -> new IllegalArgumentException("Alias not found: " + alias));

        LocalDateTime now = LocalDateTime.now();
        if (su.getExpiresAt() != null && now.isAfter(su.getExpiresAt())) {
            su.setActive(false);
            shortUrlRepository.save(su);
            throw new IllegalStateException("Link has expired");
        }

        if (su.getMaxClicks() != null && su.getClickCount() >= su.getMaxClicks()) {
            su.setActive(false);
            shortUrlRepository.save(su);
            throw new IllegalStateException("Link click limit reached");
        }

        su.setClickCount(su.getClickCount() + 1);
        shortUrlRepository.save(su);

        ClickEvent event = new ClickEvent();
        event.setShortUrl(su);
        event.setTimestamp(now);
        event.setIpAddress(request.getRemoteAddr());
        event.setUserAgent(request.getHeader("User-Agent"));
        event.setReferer(request.getHeader("Referer"));
        clickEventRepository.save(event);

        return su;
    }

    /**
     * List all mappings for a given owner (paginated).
     */
    public Page<ShortUrl> listMappings(Long ownerId, Pageable pageable) {
        return shortUrlRepository.findAllByOwnerId(ownerId, pageable);
    }

    /**
     * Update fields on an existing mapping.
     */
    public ShortUrl updateMapping(Long id,
                                  String newOriginalUrl,
                                  LocalDateTime newExpiresAt,
                                  Integer newMaxClicks,
                                  Boolean active,
                                  Long ownerId) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Deactivate (soft-delete) a mapping.
     */
    public void deactivateMapping(Long id, Long ownerId) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Gather analytics: total clicks, unique clicks, daily breakdown.
     */
    public Map<String, Object> getAnalytics(Long id, Long ownerId) {
        ShortUrl su = shortUrlRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Link not found: " + id));
        if (!su.getOwnerId().equals(ownerId)) {
            throw new IllegalArgumentException("Not authorized to view analytics for this link");
        }

        long totalClicks = clickEventRepository.countByShortUrlId(id);
        long uniqueClicks = clickEventRepository.countUniqueIps(id);
        List<Object[]> daily = clickEventRepository.countByDay(id);

        List<Map<String, Object>> clicksOverTime = daily.stream().map(arr -> {
            LocalDate date = (LocalDate) arr[0];
            Long count = (Long) arr[1];
            Map<String, Object> m = new HashMap<>();
            m.put("date", date);
            m.put("count", count);
            return m;
        }).collect(Collectors.toList());

        Map<String, Object> analytics = new HashMap<>();
        analytics.put("totalClicks", totalClicks);
        analytics.put("uniqueClicks", uniqueClicks);
        analytics.put("clicksOverTime", clicksOverTime);
        return analytics;
    }

    /**
     * Generate a random 6-character short code.
     */
    private String generateShortCode() {
        return UUID.randomUUID().toString().substring(0, 6);
    }
}

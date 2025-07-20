package com.example.shorturl.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "short_url")
public class ShortUrl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "original_url", nullable = false)
    private String originalUrl;

    @Column(name = "short_code", nullable = false, unique = true)
    private String shortCode;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    // ← NEW: who owns this link
    @Column(name = "owner_id", nullable = false)
    private Long ownerId;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    @Column(name = "max_clicks")
    private Integer maxClicks;

    @Column(name = "click_count", nullable = false)
    private Integer clickCount = 0;

    @Column(name = "active", nullable = false)
    private Boolean active = true;

    // ─── Getters & Setters ───────────────────────────────────────────

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public Integer getMaxClicks() {
        return maxClicks;
    }

    public void setMaxClicks(Integer maxClicks) {
        this.maxClicks = maxClicks;
    }

    public Integer getClickCount() {
        return clickCount;
    }

    public void setClickCount(Integer clickCount) {
        this.clickCount = clickCount;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}

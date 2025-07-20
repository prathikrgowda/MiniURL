// src/main/java/com/example/shorturl/dto/CreateLinkDto.java
package com.example.shorturl.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public class CreateLinkDto {

    @NotBlank(message = "Original URL must not be blank")
    @Pattern(
        regexp = "^https?://.+$",
        message = "Original URL must be a valid HTTP or HTTPS URL"
    )
    private String originalUrl;

    @Pattern(
        regexp = "^[A-Za-z0-9_-]{4,20}$",
        message = "Alias must be 4–20 characters: letters, numbers, underscore or hyphen"
    )
    private String alias;

    @Future(message = "Expiration date must be in the future")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime expiresAt;

    @Min(value = 1, message = "Max clicks must be at least 1")
    private Integer maxClicks;

    // ─── Getters & Setters ──────────────────────────────────────────

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
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
}

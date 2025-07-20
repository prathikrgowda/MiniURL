// src/main/java/com/example/shorturl/dto/AnalyticsDto.java
package com.example.shorturl.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.List;

public class AnalyticsDto {

    private long totalClicks;
    private long uniqueClicks;
    private List<DailyClick> clicksOverTime;

    // ─── Getters & Setters ──────────────────────────────────────────

    public long getTotalClicks() {
        return totalClicks;
    }

    public void setTotalClicks(long totalClicks) {
        this.totalClicks = totalClicks;
    }

    public long getUniqueClicks() {
        return uniqueClicks;
    }

    public void setUniqueClicks(long uniqueClicks) {
        this.uniqueClicks = uniqueClicks;
    }

    public List<DailyClick> getClicksOverTime() {
        return clicksOverTime;
    }

    public void setClicksOverTime(List<DailyClick> clicksOverTime) {
        this.clicksOverTime = clicksOverTime;
    }

    public static class DailyClick {
        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate date;
        private long count;

        // ─── Getters & Setters ──────────────────────────────────────

        public LocalDate getDate() {
            return date;
        }

        public void setDate(LocalDate date) {
            this.date = date;
        }

        public long getCount() {
            return count;
        }

        public void setCount(long count) {
            this.count = count;
        }
    }
}

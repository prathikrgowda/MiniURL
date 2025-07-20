package com.example.shorturl.repository;

import com.example.shorturl.model.ClickEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClickEventRepository extends JpaRepository<ClickEvent, Long> {

    /** Count total click events for a given ShortUrl ID */
    @Query("SELECT COUNT(e) FROM ClickEvent e WHERE e.shortUrl.id = :id")
    long countByShortUrlId(@Param("id") Long shortUrlId);

    /** Count unique IP addresses for a given ShortUrl ID */
    @Query("SELECT COUNT(DISTINCT e.ipAddress) FROM ClickEvent e WHERE e.shortUrl.id = :id")
    long countUniqueIps(@Param("id") Long shortUrlId);

    /**
     * Aggregate click counts by day for a given ShortUrl ID.
     * Returns a List of Object[] where:
     *  - [0] = java.time.LocalDate (the date)
     *  - [1] = Long (number of clicks on that date)
     */
    @Query(
      "SELECT FUNCTION('DATE', e.timestamp) AS day, COUNT(e) AS cnt " +
      "FROM ClickEvent e " +
      "WHERE e.shortUrl.id = :id " +
      "GROUP BY FUNCTION('DATE', e.timestamp) " +
      "ORDER BY day"
    )
    List<Object[]> countByDay(@Param("id") Long shortUrlId);
}

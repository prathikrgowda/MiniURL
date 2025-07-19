package com.example.shorturl.controller;

import com.example.shorturl.dto.UrlRequest;
import com.example.shorturl.dto.UrlResponse;
import com.example.shorturl.service.ShortUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/url")
public class ShortUrlController {

    private final ShortUrlService service;

    @Autowired
    public ShortUrlController(ShortUrlService service) {
        this.service = service;
    }

    /**
     * POST /api/url/shorten
     * Request:  { "originalUrl": "https://…" }
     * Response: { "shortUrl": "http://localhost:8080/abc123" }
     */
    @PostMapping("/shorten")
    public ResponseEntity<UrlResponse> shortenUrl(@RequestBody UrlRequest request) {
        // call the existing service method
        String code = service.shortenUrl(request.getOriginalUrl());
        // build the full URL for the client
        String fullShortUrl = "http://localhost:8080/api/url/" + code;
        return ResponseEntity.ok(new UrlResponse(fullShortUrl));
    }

    /**
     * GET /api/url/{code}
     * Issues a 302 redirect or 404 if not found.
     */
    @GetMapping("/{code}")
    public ResponseEntity<?> redirectToOriginal(@PathVariable String code) {
        Optional<String> original = service.getOriginalUrl(code);

        return original
          .map(url -> ResponseEntity
            .status(302)
            .header("Location", url)
            .build()
          )
          .orElse(ResponseEntity.notFound().build());
    }
}

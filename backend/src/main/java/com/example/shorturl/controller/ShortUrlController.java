package com.example.shorturl.controller;

import com.example.shorturl.dto.UrlRequest;
import com.example.shorturl.dto.UrlResponse;
import com.example.shorturl.service.ShortUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/url")
public class ShortUrlController {

    @Autowired
    private ShortUrlService service;

    @PostMapping("/shorten")
    public ResponseEntity<UrlResponse> shortenUrl(@RequestBody UrlRequest request) {
        String shortCode = service.shortenUrl(request.getOriginalUrl());
        String fullShortUrl = "http://localhost:8080/api/url/" + shortCode;
        return ResponseEntity.ok(new UrlResponse(fullShortUrl));
    }

    @GetMapping("/{code}")
    public ResponseEntity<?> redirectToOriginal(@PathVariable String code) {
        return service.getOriginalUrl(code)
                .map(url -> ResponseEntity.status(302).header("Location", url).build())
                .orElse(ResponseEntity.notFound().build());
    }
}

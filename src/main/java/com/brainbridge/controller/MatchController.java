package com.brainbridge.controller;

import com.brainbridge.service.MatchingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/matches")
public class MatchController {

    private final MatchingService matchingService;

    public MatchController(MatchingService matchingService) {
        this.matchingService = matchingService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getMatches(@PathVariable Long userId) {
        try {
            return ResponseEntity.ok(matchingService.findMatches(userId));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(java.util.Map.of("error", ex.getMessage()));
        }
    }
}

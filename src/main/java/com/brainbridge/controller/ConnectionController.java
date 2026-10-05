package com.brainbridge.controller;

import com.brainbridge.entity.ConnectionRequest;
import com.brainbridge.service.ConnectionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/connections")
public class ConnectionController {

    private final ConnectionService connectionService;

    public ConnectionController(ConnectionService connectionService) {
        this.connectionService = connectionService;
    }

    @PostMapping
    public ResponseEntity<?> send(@RequestParam Long senderId,
                                  @RequestParam Long receiverId) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(connectionService.sendRequest(senderId, receiverId));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getRequests(@PathVariable Long userId) {
        return ResponseEntity.ok(connectionService.getRequests(userId));
    }

    @PutMapping("/{id}/accept")
    public ResponseEntity<?> accept(@PathVariable Long id,
                                    @RequestParam Long receiverId) {
        try {
            return ResponseEntity.ok(connectionService.accept(id, receiverId));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
        }
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<?> reject(@PathVariable Long id,
                                    @RequestParam Long receiverId) {
        try {
            return ResponseEntity.ok(connectionService.reject(id, receiverId));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
        }
    }
}

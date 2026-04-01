package org.example.dombyra.controllers;

import lombok.RequiredArgsConstructor;
import org.example.dombyra.dto.request.WatchHistoryRequest;
import org.example.dombyra.services.WatchHistoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/history")
@RequiredArgsConstructor
public class WatchHistoryController {
    private final WatchHistoryService watchHistoryService;

    @PutMapping
    public ResponseEntity<?> saveHistory(Authentication authentication,@RequestBody WatchHistoryRequest watchHistoryRequest){
        watchHistoryService.saveHistory(authentication.getName(),watchHistoryRequest);
        return ResponseEntity.ok(Map.of("message","History saved"));
    }

    @GetMapping("/{lessonId}")
    public ResponseEntity<?> getHistory(Authentication authentication,@PathVariable Long lessonId){
        Integer seconds = watchHistoryService.getStoppedSeconds(authentication.getName(),lessonId);
        return ResponseEntity.ok(Map.of("stoppedAtSeconds",seconds));
    }
}

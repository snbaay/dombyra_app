package org.example.dombyra.dto.request;

public record WatchHistoryRequest(
        Long lessonId,
        Integer stoppedAtSeconds
){
}

package org.example.dombyra.dto.response;

public record LessonResponse(
        Long id,
        Integer lessonNumber,
        String name,
        String description,
        String duration,
        String videoUrl,
        String thumbnailUrl
) {
}

package org.example.dombyra.services;

import lombok.RequiredArgsConstructor;
import org.example.dombyra.dto.response.LessonResponse;
import org.example.dombyra.models.Lesson;
import org.example.dombyra.repositories.LessonRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LessonService {
    private final LessonRepository lessonRepository;

    public List<LessonResponse> getAllLessons(){
        List<Lesson> lessons = lessonRepository.findAll();
        return lessons.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public LessonResponse getLessonById(Long id){
        return convertToDto(lessonRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND ,"Lesson not found")));
    }
    private LessonResponse convertToDto(Lesson lesson){
        return new LessonResponse(
                lesson.getId(),
                lesson.getLessonNumber(),
                lesson.getName(),
                lesson.getDescription(),
                lesson.getDuration(),
                lesson.getVideoUrl(),
                lesson.getThumbnailUrl()
        );
    }
}

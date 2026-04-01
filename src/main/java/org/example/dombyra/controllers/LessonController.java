package org.example.dombyra.controllers;

import lombok.RequiredArgsConstructor;
import org.example.dombyra.dto.response.LessonResponse;
import org.example.dombyra.models.Lesson;
import org.example.dombyra.services.LessonService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/lessons")
@RequiredArgsConstructor
public class LessonController {
    private final LessonService lessonService;

    @GetMapping
    public List<LessonResponse> getAllLessons(){
        return lessonService.getAllLessons();
    }

    @GetMapping("{id}")
    public LessonResponse getLessonById(@PathVariable Long id){
        return lessonService.getLessonById(id);
    }
}

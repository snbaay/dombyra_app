package org.example.dombyra.services;

import lombok.RequiredArgsConstructor;
import org.example.dombyra.dto.request.WatchHistoryRequest;
import org.example.dombyra.models.Lesson;
import org.example.dombyra.models.User;
import org.example.dombyra.models.WatchHistory;
import org.example.dombyra.repositories.LessonRepository;
import org.example.dombyra.repositories.UserRepository;
import org.example.dombyra.repositories.WatchHistoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class WatchHistoryService {
    private final WatchHistoryRepository watchHistoryRepository;
    private final UserRepository userRepository;
    private final LessonRepository lessonRepository;

    public void saveHistory(String phoneNumber, WatchHistoryRequest watchHistoryRequest){
        User user  = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found"));
        Lesson lesson  = lessonRepository.findById(watchHistoryRequest.lessonId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Lesson not found"));
        WatchHistory watchHistory = watchHistoryRepository.findByUserAndLesson(user,lesson).orElse(new WatchHistory());
        watchHistory.setUser(user);
        watchHistory.setLesson(lesson);
        watchHistory.setStoppedAtSeconds(watchHistoryRequest.stoppedAtSeconds());

        watchHistoryRepository.save(watchHistory);
    }

    public Integer getStoppedSeconds(String phoneNumber, Long lessonId){
        User user  = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found"));
        Lesson lesson  = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Lesson not found"));
        return watchHistoryRepository.findByUserAndLesson(user,lesson).map(WatchHistory::getStoppedAtSeconds).orElse(0);
    }
}

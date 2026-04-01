package org.example.dombyra.repositories;

import org.example.dombyra.models.Lesson;
import org.example.dombyra.models.User;
import org.example.dombyra.models.WatchHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WatchHistoryRepository extends JpaRepository<WatchHistory,Long> {
    Optional<WatchHistory> findByUserAndLesson(User user, Lesson lesson);
}

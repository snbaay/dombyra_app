package org.example.dombyra.repositories;

import org.example.dombyra.models.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonRepository extends JpaRepository<Lesson,Long> {

}

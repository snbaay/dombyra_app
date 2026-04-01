package org.example.dombyra.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "lessons")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lesoon_id")
    private Long id;
    @Column(name = "lesson_number")
    private Integer lessonNumber;
    @Column(name = "lesson_name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "duration")
    private String duration;
    @Column(name = "video_url")
    private String videoUrl;
    @Column(name = "thumb_nail_url")
    private String thumbnailUrl;

}

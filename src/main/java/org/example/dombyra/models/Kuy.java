package org.example.dombyra.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "kuyler")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Kuy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "kuy_id")
    private Long id;
    @Column(name = "kuy_name")
    private String name;
    @Column(name = "history", columnDefinition = "TEXT")
    private String history;
    @Column(name = "audio_url")
    private String audioUrl; // Музыканың сілтемесі (Cloudinary-ден келетін)
    @Column(name = "audio_public_id")
    private String audioPublicId; // Cloudinary-ден өшіру үшін керек ID
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kuyshy_id")
    @JsonIgnoreProperties({"kuyler", "description", "photoUrl", "photoPublicId"})
    private Kuyshy kuyshy;
}

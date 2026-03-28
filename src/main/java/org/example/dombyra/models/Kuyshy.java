package org.example.dombyra.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "kuyshyler")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Kuyshy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "kuyshy_id")
    Long id;
    @Column(name = "first_name")
    String firstName;
    @Column(name = "last_name")
    String lastName;
    @Column(name = "description", columnDefinition = "TEXT")
    String description;
    @Column(name = "kuyshy_photo_url")
    private String photoUrl;
    @Column(name = "kuyshy_photo_public_id")
    private String photoPublicId; // для удаления из Cloudinary
    @OneToMany(mappedBy = "kuyshy", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Kuy> kuyler;

}

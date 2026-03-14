package org.example.dombyra.repositories;

import org.example.dombyra.dto.response.KuyshyResponse;
import org.example.dombyra.models.Kuyshy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface KuyshyRepository extends JpaRepository<Kuyshy,Long> {
    List<KuyshyResponse> findAllBy();

    Optional<KuyshyResponse> findProjectedById(Long id);

}

package org.example.dombyra.repositories;

import org.example.dombyra.dto.response.KuyResponse;
import org.example.dombyra.dto.response.KuyshyResponse;
import org.example.dombyra.models.Kuy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface KuyRepository extends JpaRepository<Kuy,Long> {

}

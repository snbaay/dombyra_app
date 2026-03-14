package org.example.dombyra.services;

import jdk.jshell.Snippet;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.dombyra.dto.response.KuyshyResponse;
import org.example.dombyra.models.Kuyshy;
import org.example.dombyra.repositories.KuyshyRepository;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class KuyshyService {
    private final KuyshyRepository kuyshyRepository;
    
    public List<KuyshyResponse> getAllKuyshy(){
        return kuyshyRepository.findAllBy();
    }
    public KuyshyResponse getKuyshy(Long id){
        return kuyshyRepository.findProjectedById(id).orElseThrow(() ->  new ResponseStatusException(HttpStatus.NOT_FOUND,"Kuyshy not found"));
    }
}

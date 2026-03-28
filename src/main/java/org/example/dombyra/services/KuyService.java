package org.example.dombyra.services;

import lombok.RequiredArgsConstructor;
import org.example.dombyra.dto.response.KuyResponse;
import org.example.dombyra.dto.response.KuyshyResponse;
import org.example.dombyra.repositories.KuyRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KuyService {
    private final KuyRepository kuyRepository;

    public List<KuyResponse> getAllKuy(){
        return kuyRepository.findAllBy();
    }
    public KuyResponse getKuy(Long id){
        return kuyRepository.findProjectedById(id).orElseThrow(() ->  new ResponseStatusException(HttpStatus.NOT_FOUND,"Kuy not found"));
    }
}

package org.example.dombyra.services;

import lombok.RequiredArgsConstructor;
import org.example.dombyra.dto.response.KuyResponse;
import org.example.dombyra.dto.response.KuyshyResponse;
import org.example.dombyra.dto.response.KuyshyShortResponse;
import org.example.dombyra.models.Kuy;
import org.example.dombyra.repositories.KuyRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KuyService {
    private final KuyRepository kuyRepository;

    public List<KuyResponse> getAllKuy(){
        List<Kuy> kuyler = kuyRepository.findAll();
        return kuyler.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public KuyResponse getKuy(Long id){
        Kuy kuy =  kuyRepository.findById(id).orElseThrow(() ->  new ResponseStatusException(HttpStatus.NOT_FOUND,"Kuy not found"));
        return convertToDto(kuy);
    }
    private KuyResponse convertToDto(Kuy kuy) {
        // Алдымен күйшінің қысқа нұсқасын жасаймыз
        KuyshyShortResponse kuyshyShort = new KuyshyShortResponse(
                kuy.getKuyshy().getId(),
                kuy.getKuyshy().getFirstName(),
                kuy.getKuyshy().getLastName()
        );

        // Сосын барып негізгі күйдің респонсын жинаймыз
        return new KuyResponse(
                kuy.getId(),
                kuy.getName(), // (Егер сенде kuyName болса, солай жаз)
                kuy.getHistory(),
                kuy.getAudioUrl(),
                kuyshyShort // Кішкентай күйшіні осында саламыз
        );
    }
}

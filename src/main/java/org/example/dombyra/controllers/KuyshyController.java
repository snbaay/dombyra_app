package org.example.dombyra.controllers;

import lombok.RequiredArgsConstructor;
import org.example.dombyra.dto.response.KuyshyResponse;
import org.example.dombyra.services.KuyshyService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/kuyshy")
@RequiredArgsConstructor
public class KuyshyController {
    private final KuyshyService kuyshyService;

    @GetMapping
    public List<KuyshyResponse> getAllKuyshy(){
        return kuyshyService.getAllKuyshy();
    }

    @GetMapping("/{id}")
    public KuyshyResponse getKuyshyById(@PathVariable Long id){
        return kuyshyService.getKuyshy(id);
    }
}

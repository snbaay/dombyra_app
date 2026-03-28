package org.example.dombyra.controllers;

import lombok.RequiredArgsConstructor;
import org.example.dombyra.dto.response.KuyResponse;
import org.example.dombyra.services.KuyService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/kuy")
@RequiredArgsConstructor
public class KuyController {
    private final KuyService kuyService;

    @GetMapping("{id}")
    public KuyResponse getKuy(@PathVariable Long id){
        return kuyService.getKuy(id);
    }

    @GetMapping
    public List<KuyResponse> getAllKuy(){
        return kuyService.getAllKuy();
    }
}

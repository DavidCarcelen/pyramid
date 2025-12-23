package com.dcin.pyramid.controller;

import com.dcin.pyramid.model.dto.GeneralResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class HealthController {
    @GetMapping
    public ResponseEntity<GeneralResponse> health(){
        return ResponseEntity.ok(new GeneralResponse("Pyramid API is running"));
    }
}

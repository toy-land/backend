package com.openhack.toyland.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openhack.toyland.dto.ToyCreateRequest;
import com.openhack.toyland.service.toy.ToyCreateService;
import lombok.RequiredArgsConstructor;

@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/api/toys")
@RestController
public class ToyController {
    private final ToyCreateService service;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid ToyCreateRequest request) {
        Long id = service.create(request);
        return ResponseEntity.created(URI.create("/api/toys/" + id)).build();
    }
}

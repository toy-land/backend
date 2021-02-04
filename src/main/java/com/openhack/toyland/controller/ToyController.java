package com.openhack.toyland.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openhack.toyland.dto.ToyCreateRequest;
import com.openhack.toyland.dto.ToyResponse;
import com.openhack.toyland.service.toy.ToyCreateService;
import com.openhack.toyland.service.toy.ToyService;
import lombok.RequiredArgsConstructor;

@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/api/toys")
@RestController
public class ToyController {
    private final ToyService service;
    private final ToyCreateService createService;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid ToyCreateRequest request) {
        Long id = createService.create(request);
        return ResponseEntity.created(URI.create("/api/toys/" + id)).build();
    }

    @GetMapping
    public ResponseEntity<List<ToyResponse>> findAll() {
        List<ToyResponse> responses = service.findAll();
        return ResponseEntity.ok(responses);
    }
}

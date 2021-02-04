package com.openhack.toyland.service.toy;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.openhack.toyland.domain.toy.ToyRepository;
import com.openhack.toyland.dto.ToyResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ToyService {
    private final ToyRepository repository;

    public List<ToyResponse> findAll() {
        return repository.findAll().stream()
            .map(ToyResponse::new)
            .collect(Collectors.toList());
    }
}

package com.openhack.toyland.service.toy;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.openhack.toyland.domain.toy.Toy;
import com.openhack.toyland.domain.toy.ToyRepository;
import com.openhack.toyland.dto.ToyDetailResponse;
import com.openhack.toyland.dto.ToyResponse;
import com.openhack.toyland.exception.EntityNotFoundException;
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

    public ToyDetailResponse findById(Long id) {
        Toy toy = repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("해당되는 toy가 없습니다."));
        return new ToyDetailResponse(toy);
    }
}

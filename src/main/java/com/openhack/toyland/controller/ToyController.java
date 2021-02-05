package com.openhack.toyland.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openhack.toyland.dto.DeleteToyRequstBody;
import com.openhack.toyland.dto.SimpleSuccessResponse;
import com.openhack.toyland.dto.SimpleToyResponse;
import com.openhack.toyland.dto.ToyCreateRequest;
import com.openhack.toyland.dto.ToyDetailResponse;
import com.openhack.toyland.dto.ToyResponse;
import com.openhack.toyland.dto.UpdateToyRequestBody;
import com.openhack.toyland.service.toy.ToyCreateService;
import com.openhack.toyland.service.toy.ToyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
        log.info(Long.toString(id));
        return ResponseEntity.created(URI.create("/api/toys/" + id)).build();
    }

    @GetMapping
    public ResponseEntity<List<ToyResponse>> findAll(
        @PageableDefault(size = 6, sort = "created_date", direction = Sort.Direction.DESC) Pageable pageable,
        @RequestParam(required = false) List<Long> organization,
        @RequestParam(required = false) List<Long> skill,
        @RequestParam(required = false) List<String> category,
        @RequestParam(required = false) List<String> period,
        @RequestParam(required = false) List<String> search
    ) {
        log.info("[get toy condition] - " + pageable.toString());
        log.info("[get toy condition] - " + organization);
        log.info("[get toy condition] - " + skill);
        log.info("[get toy condition] - " + category);
        log.info("[get toy condition] - " + period);
        log.info("[get toy condition] - " + search);
        List<ToyResponse> responses = service.findAll(pageable, organization, skill, category, period, search);
        log.info(responses.toString());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ToyDetailResponse> findById(@PathVariable Long id) {
        ToyDetailResponse response = service.findById(id);
        log.info(response.toString());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/auto-complete")
    public ResponseEntity<List<SimpleToyResponse>> autoComplete(@RequestParam(required = false) List<String> search) {
        log.info("[get toy condition] - " + search);
        List<SimpleToyResponse> responses = service.search(search);
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SimpleSuccessResponse> updateById(@PathVariable Long id, @RequestBody @Valid
        UpdateToyRequestBody updateToyRequestBody) {
        service.updateById(id, updateToyRequestBody);

        SimpleSuccessResponse response = new SimpleSuccessResponse("Toy 수정 성공");
        log.info(response.toString());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SimpleSuccessResponse> deleteById(@PathVariable Long id,
        @RequestBody @Valid DeleteToyRequstBody deleteToyRequstBody) {
        service.deleteById(id, deleteToyRequstBody);

        SimpleSuccessResponse response = new SimpleSuccessResponse("Toy 삭제 성공");
        log.info(response.toString());
        return ResponseEntity.ok(response);
    }
}

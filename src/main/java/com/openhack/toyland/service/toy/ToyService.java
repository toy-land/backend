package com.openhack.toyland.service.toy;

import java.util.List;
import java.util.stream.Collectors;
import com.openhack.toyland.domain.toy.Toy;
import com.openhack.toyland.domain.toy.ToyRepository;
import com.openhack.toyland.domain.toy.ToySearchAndSortRepository;
import com.openhack.toyland.dto.ToyDetailResponse;
import com.openhack.toyland.dto.ToyResponse;
import com.openhack.toyland.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ToyService {
	private final ToyRepository toyRepository;
	private final ToySearchAndSortRepository toySearchAndSortRepository;

	public List<ToyResponse> findAll(Pageable pageable, List<Long> organization, List<Long> skill,
		List<String> category, List<String> period, List<String> search) {
		return toySearchAndSortRepository.findToysByConditions(pageable, organization, skill, category, period, search)
			.stream()
			.map(ToyResponse::new)
			.collect(Collectors.toList());
	}

	public ToyDetailResponse findById(Long id) {
		Toy toy = toyRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("해당되는 toy가 없습니다."));
		return new ToyDetailResponse(toy);
	}
}

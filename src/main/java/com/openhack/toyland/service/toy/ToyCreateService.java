package com.openhack.toyland.service.toy;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.openhack.toyland.domain.OrganizationRepository;
import com.openhack.toyland.domain.skill.Skill;
import com.openhack.toyland.domain.skill.SkillRepository;
import com.openhack.toyland.domain.skill.TechStack;
import com.openhack.toyland.domain.skill.TechStackRepository;
import com.openhack.toyland.domain.toy.Toy;
import com.openhack.toyland.domain.toy.ToyRepository;
import com.openhack.toyland.domain.user.User;
import com.openhack.toyland.dto.ToyCreateRequest;
import com.openhack.toyland.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ToyCreateService {
    private final ToyRepository toyRepository;
    private final OrganizationRepository organizationRepository;
    private final SkillRepository skillRepository;
    private final TechStackRepository techStackRepository;

    @Transactional
    public Long create(ToyCreateRequest request) {
        if (!organizationRepository.existsById(request.getOrganizationId())) {
            throw new EntityNotFoundException("해당되는 소속이 없습니다.");
        }

        Toy toy = request.toEntity();
        Toy saved = toyRepository.save(toy);

        List<User> users = request.toUsers();
        // TODO: 2021/02/04 UserService에게 이양 with toyId.

        List<Long> techStacks = request.getTechStackIds();
        validate(techStacks);
        associate(techStacks, saved.getId());

        return saved.getId();
    }

    private void validate(List<Long> techStackIds) {
        List<Long> skillIds = skillRepository.findAll().stream()
            .map(Skill::getId)
            .collect(Collectors.toList());
        if (!skillIds.containsAll(techStackIds)) {
            throw new EntityNotFoundException("해당되는 기술 스택이 없습니다.");
        }
    }

    private void associate(List<Long> techStackIds, Long toyId) {
        List<TechStack> techStacks = techStackIds.stream()
            .map(it -> new TechStack(toyId, it))
            .collect(Collectors.toList());
        techStackRepository.saveAll(techStacks);
    }
}

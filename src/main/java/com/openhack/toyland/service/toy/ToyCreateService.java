package com.openhack.toyland.service.toy;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.openhack.toyland.domain.OrganizationRepository;
import com.openhack.toyland.domain.skill.Skill;
import com.openhack.toyland.domain.skill.SkillRepository;
import com.openhack.toyland.domain.skill.TechStack;
import com.openhack.toyland.domain.skill.TechStackRepository;
import com.openhack.toyland.domain.toy.Toy;
import com.openhack.toyland.domain.toy.ToyRepository;
import com.openhack.toyland.domain.user.Contributor;
import com.openhack.toyland.domain.user.ContributorRepository;
import com.openhack.toyland.domain.user.User;
import com.openhack.toyland.domain.user.UserRepository;
import com.openhack.toyland.dto.ToyCreateRequest;
import com.openhack.toyland.exception.DuplicatedEntityException;
import com.openhack.toyland.exception.EntityNotFoundException;
import com.openhack.toyland.service.maintenance.MaintenanceService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@Service
public class ToyCreateService {
    private final ToyRepository toyRepository;
    private final OrganizationRepository organizationRepository;
    private final SkillRepository skillRepository;
    private final TechStackRepository techStackRepository;
    private final UserRepository userRepository;
    private final ContributorRepository contributorRepository;
    private final MaintenanceService maintenanceService;

    @Transactional
    public Long create(ToyCreateRequest request) {
        if (toyRepository.existsByGithubIdentifier(request.getGithubIdentifier())) {
            throw new DuplicatedEntityException("입력하신 레포지토리로는 toy가 이미 생성되어있습니다.");
        }
        validateOrganization(request);

        Toy toy = request.toEntity();
        Toy saved = toyRepository.save(toy);

        maintenanceService.associate(LocalDateTime.parse(request.getPushedAt()), request.getServiceLink(),
            saved.getId());

        List<User> users = request.toUsers();
        List<Long> updatedIds = fetchUserIds(users);
        associateWithContributor(updatedIds, saved.getId());

        List<Long> techStacks = request.getTechStackIds();
        validate(techStacks);
        associateWithTechStack(techStacks, saved.getId());

        return saved.getId();
    }

    private void validateOrganization(ToyCreateRequest request) {
        if (!organizationRepository.existsById(request.getOrganizationId())) {
            throw new EntityNotFoundException("해당되는 소속이 없습니다.");
        }
    }

    private List<Long> fetchUserIds(List<User> users) {
        List<User> all = userRepository.findAll();
        List<Long> userGithubIdentifiers = all.stream()
            .map(User::getGithubIdentifier)
            .collect(Collectors.toList());

        Map<Boolean, List<User>> savedOrNot = users.stream()
            .collect(Collectors.groupingBy(it -> userGithubIdentifiers.contains(it.getGithubIdentifier())));

        List<User> notSaved = savedOrNot.getOrDefault(false, new ArrayList<>());
        List<Long> newlySavedIds = userRepository.saveAll(notSaved)
            .stream()
            .map(User::getId)
            .collect(Collectors.toList());

        List<User> savedUsers = savedOrNot.getOrDefault(true, new ArrayList<>());
        List<Long> savedIds = update(all, savedUsers);

        return Stream.of(newlySavedIds, savedIds)
            .flatMap(Collection::stream)
            .collect(Collectors.toList());
    }

    private List<Long> update(List<User> all, List<User> savedUsers) {
        List<Long> savedIds = new ArrayList<>();
        for (User savedUser : savedUsers) {
            userRepository.updateUsername(savedUser.getUsername(), savedUser.getGithubIdentifier());
            User user = all.stream()
                .filter(entity -> entity.isSameGithubIdentifier(savedUser))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("해당되는 User가 없습니다."));
            savedIds.add(user.getId());
        }
        return savedIds;
    }

    private void associateWithContributor(List<Long> userIds, Long toyId) {
        List<Contributor> contributors = userIds.stream()
            .map(it -> new Contributor(toyId, it))
            .collect(Collectors.toList());
        contributorRepository.saveAll(contributors);
    }

    private void validate(List<Long> techStackIds) {
        List<Long> skillIds = skillRepository.findAll().stream()
            .map(Skill::getId)
            .collect(Collectors.toList());
        if (!skillIds.containsAll(techStackIds)) {
            throw new EntityNotFoundException("해당되는 기술 스택이 없습니다.");
        }
    }

    private void associateWithTechStack(List<Long> techStackIds, Long toyId) {
        List<TechStack> techStacks = techStackIds.stream()
            .map(it -> new TechStack(toyId, it))
            .collect(Collectors.toList());
        techStackRepository.saveAll(techStacks);
    }
}

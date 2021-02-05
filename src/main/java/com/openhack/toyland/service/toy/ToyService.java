package com.openhack.toyland.service.toy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.openhack.toyland.domain.MaintenanceRepository;
import com.openhack.toyland.domain.Organization;
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
import com.openhack.toyland.dto.DeleteToyRequstBody;
import com.openhack.toyland.dto.SimpleToyResponse;
import com.openhack.toyland.dto.ToyCreateRequest;
import com.openhack.toyland.dto.ToyDetailResponse;
import com.openhack.toyland.dto.ToyResponse;
import com.openhack.toyland.dto.UpdateToyRequestBody;
import com.openhack.toyland.dto.UserResponse;
import com.openhack.toyland.exception.EntityNotFoundException;
import com.openhack.toyland.exception.InvalidRequestBodyException;
import com.openhack.toyland.exception.UnAuthorizedEventException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ToyService {
    private final ToyRepository toyRepository;
    private final OrganizationRepository organizationRepository;
    private final TechStackRepository techStackRepository;
    private final SkillRepository skillRepository;
    private final ContributorRepository contributorRepository;
    private final UserRepository userRepository;
    private final MaintenanceRepository maintenanceRepository;

    @Transactional(readOnly = true)
    public List<ToyResponse> findAll(Pageable pageable, List<Long> organizationIds, List<Long> skillIds,
        List<String> category, List<String> period, List<String> search) {

        List<Toy> toys = toyRepository.findAll().stream()
            .filter(toy -> organizationIds == null || organizationIds.contains(toy.getOrganizationId()))
            .filter(toy -> category == null || category.contains(toy.getCategory().name()))
            .filter(toy -> period == null || period.contains(toy.getPeriod().name()))
            .filter(toy -> isSearchable(toy.getTitle(), search) || isSearchable(toy.getDescription(), search))
            .collect(Collectors.toList()); // organization-filter, category-filter, period-filter, 검색
        List<Skill> skills = skillIds == null ? null : skillRepository.findAllById(skillIds);

        List<ToyResponse> answer = new LinkedList<>();

        toys.forEach(toy -> {
            List<Long> toySkillIds = techStackRepository.findAllByToyId(toy.getId()).stream()
                .map(TechStack::getSkillId).collect(Collectors.toList());
            List<Skill> toySkills = skillRepository.findAllById(toySkillIds);

            if (isSkillContain(toySkills, skills)) {
                answer.add(new ToyResponse(toy, maintenanceRepository.findByToyId(toy.getId()).get(), toySkills));
            }
        });

        PagedListHolder<ToyResponse> page = new PagedListHolder<>(answer);
        page.setPageSize(pageable.getPageSize());
        page.setPage(pageable.getPageNumber());

        return page.getPageList();
    }

    private boolean isSkillContain(List<Skill> userSkill, List<Skill> targetSkill) {
        if (targetSkill == null) {
            return true;
        }

        for (Skill skill : userSkill) {
            if (targetSkill.contains(skill)) {
                return true;
            }
        }
        return false;
    }

    private boolean isSearchable(String texts, List<String> targets) {
        if (targets == null) {
            return true;
        }
        for (String target : targets) {
            if (texts.contains(target)) {
                return true;
            }
        }
        return false;
    }

    @Transactional(readOnly = true)
    public ToyDetailResponse findById(Long id) {
        Toy toy = toyRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("해당되는 toy가 없습니다."));

        Organization organization = organizationRepository.findById(toy.getOrganizationId())
            .orElseThrow(() -> new EntityNotFoundException("해당되는 소속이 없습니다."));

        List<String> skillNames = fetchSkillNames(toy);

        List<UserResponse> userResponses = fetchUserResponses(toy);

        return new ToyDetailResponse(toy, organization, skillNames, userResponses);
    }

    private List<String> fetchSkillNames(Toy toy) {
        List<TechStack> techStacks = techStackRepository.findAllById(Collections.singletonList(toy.getId()));
        List<Skill> skills = skillRepository.findAllById(techStacks.stream()
            .map(TechStack::getSkillId)
            .collect(Collectors.toList()));
        return skills.stream()
            .map(Skill::getName)
            .collect(Collectors.toList());
    }

    private List<UserResponse> fetchUserResponses(Toy toy) {
        List<Contributor> contributors = contributorRepository.findAllById(Collections.singletonList(toy.getId()));
        List<User> users = userRepository.findAllById(contributors.stream()
            .map(Contributor::getUserId)
            .collect(Collectors.toList()));
        return users.stream()
            .map(UserResponse::from)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<SimpleToyResponse> search(List<String> search) {
        return toyRepository.findAll().stream()
            .filter(toy -> isSearchable(toy.getTitle(), search) || isSearchable(toy.getDescription(), search))
            .map(SimpleToyResponse::from)
            .collect(Collectors.toList());
    }

    @Transactional
    public void updateById(Long id, UpdateToyRequestBody updateToyRequestBody) {
        Toy toy = toyRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Toy entity를 찾을 수 없습니다."));

        checkPassword(toy, updateToyRequestBody.getPassword());

        if (!toy.getGithubIdentifier().equals(updateToyRequestBody.getGithubIdentifier())) {
            throw new InvalidRequestBodyException("github identification은 수정 불가");
        }

        Toy newToy = updateToyRequestBody.toEntity(toy.getId());
        toyRepository.save(newToy);
        techStackRepository.deleteAllByToyId(toy.getId());
        contributorRepository.deleteAllByToyId(toy.getId());

        List<User> users = updateToyRequestBody.toUsers();
        List<Long> updatedIds = fetchUserIds(users);
        associateWithContributor(updatedIds, newToy.getId());

        List<Long> techStacks = updateToyRequestBody.getTechStackIds();
        validate(techStacks);
        associateWithTechStack(techStacks, newToy.getId());
    }

    @Transactional
    public void deleteById(Long id, String password) {
        Toy toy = toyRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Toy entity를 찾을 수 없습니다."));

        checkPassword(toy, password);
        toyRepository.delete(toy);
        maintenanceRepository.deleteByToyId(toy.getId());
        techStackRepository.deleteAllByToyId(toy.getId());
        contributorRepository.deleteAllByToyId(toy.getId());
    }

    private void checkPassword(Toy toy, String password) {
        if (!StringUtils.equals(toy.getPassword(), password)) {
            throw new UnAuthorizedEventException();
        }
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

    @Transactional(readOnly = true)
    public Toy findEntityByIdAndEmailIsNotNull(Long id) {
        return toyRepository.findByIdAndEmailIsNotNull(id)
            .orElseThrow(() -> new EntityNotFoundException("toy를 찾을 수 없습니다"));
    }
}

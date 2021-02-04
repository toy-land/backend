package com.openhack.toyland.service.toy;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.openhack.toyland.domain.Organization;
import com.openhack.toyland.domain.OrganizationRepository;
import com.openhack.toyland.domain.skill.Skill;
import com.openhack.toyland.domain.skill.SkillRepository;
import com.openhack.toyland.domain.skill.TechStack;
import com.openhack.toyland.domain.skill.TechStackRepository;
import com.openhack.toyland.domain.toy.Toy;
import com.openhack.toyland.domain.toy.ToyRepository;
import com.openhack.toyland.domain.toy.ToySearchAndSortRepository;
import com.openhack.toyland.domain.user.Contributor;
import com.openhack.toyland.domain.user.ContributorRepository;
import com.openhack.toyland.domain.user.User;
import com.openhack.toyland.domain.user.UserRepository;
import com.openhack.toyland.dto.ToyDetailResponse;
import com.openhack.toyland.dto.ToyResponse;
import com.openhack.toyland.dto.UserResponse;
import com.openhack.toyland.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ToyService {
    private final ToyRepository toyRepository;
    private final ToySearchAndSortRepository toySearchAndSortRepository;
    private final OrganizationRepository organizationRepository;
    private final TechStackRepository techStackRepository;
    private final SkillRepository skillRepository;
    private final ContributorRepository contributorRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<ToyResponse> findAll(Pageable pageable, List<Long> organization, List<Long> skill,
        List<String> category, List<String> period, List<String> search) {
        return toySearchAndSortRepository.findToysByConditions(pageable, organization, skill, category, period, search)
            .stream()
            .map(ToyResponse::new)
            .collect(Collectors.toList());
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
}

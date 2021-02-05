package com.openhack.toyland.service.toy;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
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
import com.openhack.toyland.dto.ToyDetailResponse;
import com.openhack.toyland.dto.ToyResponse;
import com.openhack.toyland.dto.UserResponse;
import com.openhack.toyland.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        PagedListHolder<ToyResponse> page = new PagedListHolder(answer);
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
}

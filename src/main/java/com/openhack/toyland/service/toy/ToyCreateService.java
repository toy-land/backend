package com.openhack.toyland.service.toy;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.openhack.toyland.domain.OrganizationRepository;
import com.openhack.toyland.domain.toy.Toy;
import com.openhack.toyland.domain.toy.ToyRepository;
import com.openhack.toyland.domain.user.User;
import com.openhack.toyland.dto.ToyCreateRequest;
import com.openhack.toyland.exception.EntityNotFoundException;

@Service
public class ToyCreateService {
    private final ToyRepository toyRepository;
    private final OrganizationRepository organizationRepository;

    public ToyCreateService(ToyRepository toyRepository,
        OrganizationRepository organizationRepository) {
        this.toyRepository = toyRepository;
        this.organizationRepository = organizationRepository;
    }

    @Transactional
    public Long create(ToyCreateRequest request) {
        if (organizationRepository.existsById(request.getOrganizationId())) {
            throw new EntityNotFoundException("해당되는 소속이 없습니다.");
        }

        Toy toy = request.toEntity();
        Toy saved = toyRepository.save(toy);

        List<User> users = request.toUsers();
        // TODO: 2021/02/04 UserService에게 이양 with toyId.

        List<Long> techStacks = request.getTechStacks();
        // TODO: 2021/02/04 SkillService에게 이양 with toyId.

        return saved.getId();
    }
}

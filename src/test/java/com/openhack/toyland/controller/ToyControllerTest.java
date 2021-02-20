package com.openhack.toyland.controller;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Collections;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import com.openhack.toyland.IntegrationTest;
import com.openhack.toyland.domain.Maintenance;
import com.openhack.toyland.domain.MaintenanceRepository;
import com.openhack.toyland.domain.toy.Toy;
import com.openhack.toyland.domain.toy.ToyRepository;
import com.openhack.toyland.dto.ContributorCreateRequest;
import com.openhack.toyland.dto.ToyCreateRequest;

@SpringBootTest
class ToyControllerTest extends IntegrationTest {
    @Autowired
    private ToyRepository toyRepository;

    @Autowired
    private MaintenanceRepository maintenanceRepository;

    @DisplayName("Toy를 생성할 때에 Maintenance도 생성되는지 확인한다")
    @Test
    void create() throws Exception {
        ContributorCreateRequest contributor = new ContributorCreateRequest(123L, "toneyparky", "https://id.com");

        ToyCreateRequest toyCreateRequest = new ToyCreateRequest(123L, "title", "password", "description", "readme",
            "logoUrl", "githubLink", "serviceLink",
            "email", 1L, "AI", 1L, "2021-01-18T16:36:27", Collections.singletonList(contributor),
            Collections.singletonList(1L));

        MvcResult mvcResult = mockMvc.perform(post("/api/toys")
            .content(objectMapper.writeValueAsString(toyCreateRequest))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andReturn();

        String redirectedUrl = mvcResult.getResponse().getRedirectedUrl();
        Long toyId = Long.parseLong(redirectedUrl.substring(10));

        Toy toy = toyRepository.findById(toyId).get();
        Maintenance maintenance = maintenanceRepository.findByToyId(toyId).get();

        assertAll(
            () -> assertThat(toy.getId()).isEqualTo(toyId),
            () -> assertThat(maintenance.getId()).isNotNull(),
            () -> assertThat(maintenance.getCreatedDate()).isNotNull()
        );
    }
}
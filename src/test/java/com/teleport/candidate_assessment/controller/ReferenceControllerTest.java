package com.teleport.candidate_assessment.controller;

import com.teleport.candidate_assessment.dto.ReferenceRequestDTO;
import com.teleport.candidate_assessment.dto.ReferenceResponseDTO;
import com.teleport.candidate_assessment.service.ReferenceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReferenceControllerTest {

    @Mock
    private ReferenceService referenceService;

    @InjectMocks
    private ReferenceController referenceController;

    private WebTestClient webTestClient;

    private ReferenceRequestDTO requestDTO;
    private ReferenceResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        webTestClient = WebTestClient.bindToController(referenceController).build();
        requestDTO = new ReferenceRequestDTO("user1", "project1", "task1");
        responseDTO = new ReferenceResponseDTO("refId", "user1", "project1", "task1");
    }

    @Test
    void shouldCreateReferenceSuccessfully() {
        when(referenceService.createReference(requestDTO)).thenReturn(Mono.just(responseDTO));

        webTestClient.post()
                .uri("/api/references")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestDTO)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo("refId")
                .jsonPath("$.userId").isEqualTo("user1")
                .jsonPath("$.projectId").isEqualTo("project1")
                .jsonPath("$.taskId").isEqualTo("task1");

        verify(referenceService, times(1)).createReference(requestDTO);
    }

    @Test
    void shouldGetByUserIdSuccessfully() {
        when(referenceService.getByUserId("user1")).thenReturn(Flux.just(responseDTO));

        webTestClient.get()
                .uri("/api/references/user/user1")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ReferenceResponseDTO.class).hasSize(1);
    }

    @Test
    void shouldGetByProjectIdSuccessfully() {
        when(referenceService.getByProjectId("project1")).thenReturn(Flux.just(responseDTO));

        webTestClient.get()
                .uri("/api/references/project/project1")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ReferenceResponseDTO.class).hasSize(1);
    }

    @Test
    void shouldGetByTaskIdSuccessfully() {
        when(referenceService.getByTaskId("task1")).thenReturn(Flux.just(responseDTO));

        webTestClient.get()
                .uri("/api/references/task/task1")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ReferenceResponseDTO.class).hasSize(1);
    }
}

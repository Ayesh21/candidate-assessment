package com.teleport.candidate_assessment.service;

import static org.mockito.Mockito.*;

import com.teleport.candidate_assessment.dto.ReferenceRequestDTO;
import com.teleport.candidate_assessment.dto.ReferenceResponseDTO;
import com.teleport.candidate_assessment.entity.Reference;
import com.teleport.candidate_assessment.repository.ReferenceRepository;
import com.teleport.candidate_assessment.service.impl.ReferenceServiceImpl;
import com.teleport.candidate_assessment.transformer.ReferenceTransformer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class ReferenceServiceTest {

    @Mock
    private ReferenceRepository referenceRepository;

    @Mock
    private ReferenceTransformer referenceTransformer;

    @InjectMocks
    private ReferenceServiceImpl referenceService;

    private Reference reference;
    private ReferenceRequestDTO requestDTO;
    private ReferenceResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        requestDTO = new ReferenceRequestDTO("user1", "project1", "task1");
        reference = new Reference("refId1", "user1", "project1", "task1");
        responseDTO = new ReferenceResponseDTO("refId1", "user1", "project1", "task1");
    }

    @Test
    void shouldCreateReferenceSuccessfully() {
        when(referenceTransformer.toEntity(requestDTO)).thenReturn(reference);
        when(referenceRepository.save(reference)).thenReturn(Mono.just(reference));
        when(referenceTransformer.toResponse(reference)).thenReturn(responseDTO);

        Mono<ReferenceResponseDTO> result = referenceService.createReference(requestDTO);

        StepVerifier.create(result)
                .expectNext(responseDTO)
                .verifyComplete();

        verify(referenceRepository).save(reference);
        verify(referenceTransformer).toEntity(requestDTO);
        verify(referenceTransformer).toResponse(reference);
    }

    @Test
    void shouldReturnReferencesByUserId() {
        when(referenceRepository.findByUserId("user1")).thenReturn(Flux.just(reference));
        when(referenceTransformer.toResponse(reference)).thenReturn(responseDTO);

        Flux<ReferenceResponseDTO> result = referenceService.getByUserId("user1");

        StepVerifier.create(result)
                .expectNext(responseDTO)
                .verifyComplete();

        verify(referenceRepository).findByUserId("user1");
        verify(referenceTransformer).toResponse(reference);
    }

    @Test
    void shouldReturnReferencesByProjectId() {
        when(referenceRepository.findByProjectId("project1")).thenReturn(Flux.just(reference));
        when(referenceTransformer.toResponse(reference)).thenReturn(responseDTO);

        Flux<ReferenceResponseDTO> result = referenceService.getByProjectId("project1");

        StepVerifier.create(result)
                .expectNext(responseDTO)
                .verifyComplete();

        verify(referenceRepository).findByProjectId("project1");
        verify(referenceTransformer).toResponse(reference);
    }

    @Test
    void shouldReturnReferencesByTaskId() {
        when(referenceRepository.findByTaskId("task1")).thenReturn(Flux.just(reference));
        when(referenceTransformer.toResponse(reference)).thenReturn(responseDTO);

        Flux<ReferenceResponseDTO> result = referenceService.getByTaskId("task1");

        StepVerifier.create(result)
                .expectNext(responseDTO)
                .verifyComplete();

        verify(referenceRepository).findByTaskId("task1");
        verify(referenceTransformer).toResponse(reference);
    }
}

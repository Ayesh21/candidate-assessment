package com.teleport.candidate_assessment.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teleport.candidate_assessment.dto.ProjectRequestDTO;
import com.teleport.candidate_assessment.dto.ProjectResponseDTO;
import com.teleport.candidate_assessment.service.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/** The type Project controller test. */
@ExtendWith(MockitoExtension.class)
class ProjectControllerTest {

  @Mock private ProjectService projectService;

  @InjectMocks private ProjectController projectController;

  private MockMvc mockMvc;
  private final ObjectMapper objectMapper = new ObjectMapper();

  private ProjectRequestDTO sampleRequest;
  private ProjectResponseDTO sampleResponse;

  /** Sets up. */
  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(projectController).build();

    sampleRequest = new ProjectRequestDTO("Project Alpha", "user123");
    sampleResponse = new ProjectResponseDTO("proj1", "Project Alpha", "user123");
  }

  /**
   * Should create project successfully.
   *
   * @throws Exception the exception
   */
  @Test
  void shouldCreateProjectSuccessfully() throws Exception {
    when(projectService.create(sampleRequest)).thenReturn(sampleResponse);

    mockMvc
        .perform(
            post("/api/projects")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sampleRequest)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value("proj1"))
        .andExpect(jsonPath("$.name").value("Project Alpha"))
        .andExpect(jsonPath("$.ownerId").value("user123"));
  }

  /**
   * Should fetch project by id successfully.
   *
   * @throws Exception the exception
   */
  @Test
  void shouldFetchProjectByIdSuccessfully() throws Exception {
    when(projectService.getProjectById("proj1")).thenReturn(sampleResponse);

    mockMvc
        .perform(get("/api/projects/proj1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value("proj1"))
        .andExpect(jsonPath("$.name").value("Project Alpha"))
        .andExpect(jsonPath("$.ownerId").value("user123"));
  }
}

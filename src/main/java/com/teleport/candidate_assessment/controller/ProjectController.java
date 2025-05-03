package com.teleport.candidate_assessment.controller;

import com.teleport.candidate_assessment.dto.ProjectRequestDTO;
import com.teleport.candidate_assessment.dto.ProjectResponseDTO;
import com.teleport.candidate_assessment.dto.TaskRequestDTO;
import com.teleport.candidate_assessment.dto.TaskResponseDTO;
import com.teleport.candidate_assessment.service.ProjectService;
import com.teleport.candidate_assessment.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    private final TaskService taskService;

    @PostMapping
    public ProjectResponseDTO create(@RequestBody ProjectRequestDTO projectRequestDTO) {
        return projectService.create(projectRequestDTO);
    }

    @GetMapping("/{projectId}")
    public ProjectResponseDTO getProjectById(@PathVariable String projectId) {
        return projectService.getProjectById(projectId);
    }

    @PostMapping("/{projectId}/tasks")
    public TaskResponseDTO createTask(@PathVariable String projectId, @RequestBody TaskRequestDTO taskRequestDTO) {
        return taskService.createTask(projectId,taskRequestDTO);
    }

    @GetMapping("/{projectId}/tasks")
    public Page<TaskResponseDTO> getFilteredTasks(@PathVariable String projectId, @RequestParam String status, @RequestParam String priority, Pageable pageable) {
        return taskService.getFilteredTasks(projectId, status, priority, pageable);
    }

}

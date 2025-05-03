package com.teleport.candidate_assessment.controller;

import com.teleport.candidate_assessment.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("api/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @PatchMapping("/{id}/status")
    public void updateStatus(@PathVariable String id, @RequestBody Map<String, String> body) {
        taskService.updateStatus(id, body.get("status"));
    }

    @GetMapping("/overdue")
    public Page<?> overdue(Pageable pageable) {
        return taskService.getOverdue(pageable);
    }
}

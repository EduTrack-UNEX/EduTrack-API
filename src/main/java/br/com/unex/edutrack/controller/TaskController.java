package br.com.unex.edutrack.controller;

import br.com.unex.edutrack.dto.task.TaskResponseDto;
import br.com.unex.edutrack.model.User;
import br.com.unex.edutrack.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/subjects/{subjectId}/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskResponseDto> getTaskDetails(
            @PathVariable Integer subjectId,
            @PathVariable Integer taskId,
            @AuthenticationPrincipal User user
    ) {
        TaskResponseDto response = taskService.getTaskDetails(subjectId, taskId, user.getId());
        return ResponseEntity.ok(response);
    }
}

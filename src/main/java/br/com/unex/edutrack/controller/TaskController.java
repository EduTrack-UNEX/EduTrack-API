package br.com.unex.edutrack.controller;

import br.com.unex.edutrack.dto.ApiResponse;
import br.com.unex.edutrack.dto.task.TaskRequestDto;
import br.com.unex.edutrack.dto.task.TaskResponseDto;
import br.com.unex.edutrack.service.TaskService;
import br.com.unex.edutrack.util.ResponseUtil;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/v1/subjects/{subjectId}/tasks")
@RestController
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<TaskResponseDto>> createTask(
            @PathVariable int subjectId,
            @Valid @RequestBody TaskRequestDto request){
        TaskResponseDto task = taskService.saveTask(subjectId,request);
        return ResponseUtil.created("Tarefa Criada com sucesso",task);
    }
}

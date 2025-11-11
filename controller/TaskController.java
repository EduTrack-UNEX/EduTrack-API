package br.com.unex.edutrack.controller;

import br.com.unex.edutrack.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/subjects/{subjectId}/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable int subjectId, @PathVariable int taskId) {
        taskService.deleteTask(subjectId, taskId);
        return ResponseEntity.noContent().build();
    }
}

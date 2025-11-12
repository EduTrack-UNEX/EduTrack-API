package br.com.unex.edutrack.controller;

import br.com.unex.edutrack.dto.ApiResponse;
import br.com.unex.edutrack.dto.subject.SubjectRequestDto;
import br.com.unex.edutrack.dto.subject.SubjectResponseDto;
import br.com.unex.edutrack.dto.task.TaskRequestDto;
import br.com.unex.edutrack.dto.task.TaskResponseDto;
import br.com.unex.edutrack.service.SubjectService;

import br.com.unex.edutrack.util.ResponseUtil;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/v1/subjects")
@RestController
public class SubjectController {

    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<SubjectResponseDto>> createSubject(@Valid @RequestBody SubjectRequestDto request) {
        SubjectResponseDto subject = subjectService.saveSubject(request);
        return ResponseUtil.created("Disciplina Criada com sucesso",subject);

    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SubjectResponseDto>> findSubjectById(@Valid @PathVariable int id) {
        SubjectResponseDto subject = subjectService.getSubjectId(id);
        return ResponseUtil.ok("Disciplina encontrada com sucesso",subject);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<SubjectResponseDto>>> findSubjectAll(){
        List<SubjectResponseDto> subjects = subjectService.getSubjects();
        return ResponseUtil.ok("Lista de disciplinas retornada com sucesso",subjects);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubject (@PathVariable int id){
        subjectService.deleteSubjectById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{subjectId}/task")
    public ResponseEntity<ApiResponse<TaskResponseDto>> createTask(
            @PathVariable int subjectId,
            @Valid @RequestBody TaskRequestDto request){
        TaskResponseDto task = subjectService.saveTask(subjectId,request);
        return ResponseUtil.created("Tarefa Criada com sucesso",task);
    }

    @PatchMapping("/{subjectId}/tasks/{taskId}")
    public ResponseEntity<ApiResponse<TaskResponseDto>> updateTask(
            @PathVariable int subjectId,
            @PathVariable int taskId,
            @Valid @RequestBody TaskRequestDto request) {

        TaskResponseDto task = subjectService.updateTask(subjectId,taskId,request);
        return ResponseUtil.ok("Tarefa atualizada com sucesso.",task);
    }
}

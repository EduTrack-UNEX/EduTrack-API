package br.com.unex.edutrack.controller;

import br.com.unex.edutrack.dto.ApiResponse;
import br.com.unex.edutrack.dto.subject.SubjectRequestDto;
import br.com.unex.edutrack.dto.subject.SubjectResponseDto;
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
        return ResponseUtil.ok("Disciplina encontrado com sucesso",subject);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<SubjectResponseDto>>> findSubjectAll(){
        List<SubjectResponseDto> subjects = subjectService.getSubjects();
        return ResponseUtil.ok("Lista de disciplina retornada com sucesso",subjects);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubject (@PathVariable int id){
        subjectService.deleteSubjectById(id);
        return ResponseEntity.noContent().build();
    }
}

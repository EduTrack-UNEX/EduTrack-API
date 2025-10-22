package br.com.unex.edutrack.controller;

import br.com.unex.edutrack.dto.ApiResponse;
import br.com.unex.edutrack.dto.subject.SubjectRequestDto;
import br.com.unex.edutrack.dto.subject.SubjectResponseDto;
import br.com.unex.edutrack.service.SubjectService;
import br.com.unex.edutrack.util.ResponseUtil;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

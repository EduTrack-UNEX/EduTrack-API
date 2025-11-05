package br.com.unex.edutrack.service;

import br.com.unex.edutrack.dto.subject.SubjectRequestDto;
import br.com.unex.edutrack.dto.subject.SubjectResponseDto;
import br.com.unex.edutrack.exception.ForbiddenException;
import br.com.unex.edutrack.mapper.subject.SubjectMapper;

import br.com.unex.edutrack.model.Subject;
import br.com.unex.edutrack.model.User;
import br.com.unex.edutrack.repository.SubjectRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final SubjectMapper subjectMapper;
    private final UserService userService;


    public SubjectService(SubjectRepository subjectRepository, SubjectMapper subjectMapper, UserService userService) {
        this.subjectRepository = subjectRepository;
        this.subjectMapper = subjectMapper;
        this.userService = userService;
    }



    public SubjectResponseDto saveSubject(SubjectRequestDto data){

        User user = userService.getAuthenticatedUser();
        Subject subject = subjectMapper.toSubject(data);
        subject.setUser(user);
        Subject persistedSubject = subjectRepository.save(subject);
        return subjectMapper.toSubjectResponseDto(persistedSubject);
    }

    public List<SubjectResponseDto> getSubjects() {
        User user = userService.getAuthenticatedUser();
        return subjectRepository.findAllByUser(user)
                .stream()
                .map(subjectMapper::toSubjectResponseDto)
                .toList();
    }

    public SubjectResponseDto getSubjectId(int id) {
        User user = userService.getAuthenticatedUser();
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Disciplina não encontrada com o ID: " + id));

        if (subject.getUser().getId() != user.getId()) {
            throw new ForbiddenException("Você não tem permissão para visualizar esta disciplina");
        }

        return subjectMapper.toSubjectResponseDto(subject);
    }

    public void deleteSubjectById(int id) {
        User user = userService.getAuthenticatedUser();

        Subject subject = subjectRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Disciplina não encontrada com o ID: " + id));

        if(subject.getUser().getId() != user.getId()) {
            throw new ForbiddenException("Você não tem permissão para excluir esta disciplina");
        }

        subjectRepository.delete(subject);
    }


}

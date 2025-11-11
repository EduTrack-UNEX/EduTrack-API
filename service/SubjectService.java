package br.com.unex.edutrack.service;

import br.com.unex.edutrack.dto.subject.SubjectRequestDto;
import br.com.unex.edutrack.dto.subject.SubjectResponseDto;
import br.com.unex.edutrack.mapper.subject.SubjectMapper;

import br.com.unex.edutrack.model.Subject;
import br.com.unex.edutrack.model.User;
import br.com.unex.edutrack.repository.SubjectRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final SubjectMapper subjectMapper;


    public SubjectService(SubjectRepository subjectRepository, SubjectMapper subjectMapper) {
        this.subjectRepository = subjectRepository;
        this.subjectMapper = subjectMapper;
    }

    private User getAuthenticatedUser(){
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public SubjectResponseDto saveSubject(SubjectRequestDto data){

        User user = getAuthenticatedUser();
        Subject subject = subjectMapper.toSubject(data);
        subject.setUser(user);
        Subject persistedSubject = subjectRepository.save(subject);
        return subjectMapper.toSubjectResponseDto(persistedSubject);
    }

    public List<SubjectResponseDto> getSubjects() {
        User user = getAuthenticatedUser();
        return subjectRepository.findAllByUser(user)
                .stream()
                .map(subjectMapper::toSubjectResponseDto)
                .toList();
    }

    public SubjectResponseDto getSubjectId(int id) {
        User user = getAuthenticatedUser();
        Subject subject = subjectRepository.findByIdAndUser(id,user)
                .orElseThrow(() ->
                        new EntityNotFoundException(("Disciplina não encontrada com o ID: "+id)));
                return subjectMapper.toSubjectResponseDto(subject);
    }

    public void deleteSubjectById(int id) {
        User user = getAuthenticatedUser();

        Subject subject = subjectRepository.findByIdAndUser(id, user)
                        .orElseThrow(() -> new EntityNotFoundException("Disciplina não encontrada com o ID: " + id));
        subjectRepository.deleteById(id);
    }
}

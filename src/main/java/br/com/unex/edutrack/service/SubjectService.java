package br.com.unex.edutrack.service;

import br.com.unex.edutrack.dto.subject.SubjectRequestDto;
import br.com.unex.edutrack.dto.subject.SubjectResponseDto;
import br.com.unex.edutrack.dto.task.TaskEditRequestDto;
import br.com.unex.edutrack.dto.task.TaskRequestDto;
import br.com.unex.edutrack.dto.task.TaskResponseDto;
import br.com.unex.edutrack.exception.ForbiddenException;
import br.com.unex.edutrack.mapper.subject.SubjectMapper;

import br.com.unex.edutrack.mapper.task.TaskMapper;
import br.com.unex.edutrack.model.Subject;
import br.com.unex.edutrack.model.Task;
import br.com.unex.edutrack.model.User;
import br.com.unex.edutrack.repository.SubjectRepository;
import br.com.unex.edutrack.repository.TaskRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final SubjectMapper subjectMapper;
    private final UserService userService;
    private final TaskMapper taskMapper;
    private final TaskRepository taskRepository;


    public SubjectService(SubjectRepository subjectRepository, SubjectMapper subjectMapper,
                          UserService userService, TaskMapper taskMapper, TaskRepository taskRepository) {
        this.subjectRepository = subjectRepository;
        this.subjectMapper = subjectMapper;
        this.userService = userService;
        this.taskMapper = taskMapper;
        this.taskRepository = taskRepository;
    }

    @Transactional
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

    @Transactional
    public void deleteSubjectById(int id) {
        User user = userService.getAuthenticatedUser();

        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Disciplina não encontrada com o ID: " + id));

        if(subject.getUser().getId() != user.getId()) {
            throw new ForbiddenException("Você não tem permissão para excluir esta disciplina");
        }

        subjectRepository.delete(subject);
    }

    @Transactional
    public TaskResponseDto saveTask(int subjectId, TaskRequestDto data) {

        User user = userService.getAuthenticatedUser();

        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new EntityNotFoundException("Disciplina não encontrada com o ID: " + subjectId));
        if (subject.getUser().getId() != user.getId()) {
            throw new ForbiddenException("Você não tem permissão para visualizar esta disciplina");
        }

        Task task = taskMapper.toTask(data);
        task.setSubject(subject);
        Task savedTask = taskRepository.save(task);
        subject.addTask(savedTask);
        subjectRepository.save(subject);
        return taskMapper.toTaskResponseDto(savedTask);
    }

    @Transactional
    public TaskResponseDto updateTask(int subjectId, int taskId, TaskEditRequestDto data){

        User user = userService.getAuthenticatedUser();

        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(()-> new EntityNotFoundException("Disciplina não encontrada com o ID: " + subjectId));
        if (subject.getUser().getId() != user.getId()) {
            throw new ForbiddenException("Você não tem permissão para visualizar esta disciplina");
        }

        Task task = subject.updateWith(taskId,data);
        subjectRepository.save(subject);
        return taskMapper.toTaskResponseDto(task);
    }


}

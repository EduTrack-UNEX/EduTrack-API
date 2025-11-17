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
import br.com.unex.edutrack.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final UserRepository userRepository;


    public SubjectService(SubjectRepository subjectRepository, SubjectMapper subjectMapper,
                          UserService userService, TaskMapper taskMapper, TaskRepository taskRepository, UserRepository userRepository) {
        this.subjectRepository = subjectRepository;
        this.subjectMapper = subjectMapper;
        this.userService = userService;
        this.taskMapper = taskMapper;
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
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
        subject.addTask(task);
        subjectRepository.save(subject);

        return taskMapper.toTaskResponseDto(task);
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

    @Transactional(readOnly = true)
    public Page<TaskResponseDto> getTasksBySubject(int subjectId, Pageable pageable) {
        var user = userService.getAuthenticatedUser();

        var subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new EntityNotFoundException("Disciplina não encontrada com o ID: " + subjectId));

        if (subject.getUser().getId() != user.getId()) {
            throw new EntityNotFoundException("Disciplina não encontrada ou não pertence ao usuário.");
        }

        return taskRepository.findBySubjectOrderByIsCompletedAscIdAsc(subject, pageable)
                .map(taskMapper::toTaskResponseDto);
    }

    @Transactional
    public void deleteTask(int subjectId, int taskId) {

        User user = userService.getAuthenticatedUser();

        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new EntityNotFoundException("Disciplina não encontrada."));

        if (subject.getUser().getId() != user.getId()) {
            throw new EntityNotFoundException("Disciplina não encontrada ou não pertence ao usuário.");
        }
        
        subject.removeTask(taskId);

        subjectRepository.save(subject);
    }

}

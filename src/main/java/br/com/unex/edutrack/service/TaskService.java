package br.com.unex.edutrack.service;

import br.com.unex.edutrack.dto.task.TaskRequestDto;
import br.com.unex.edutrack.dto.task.TaskResponseDto;
import br.com.unex.edutrack.exception.ForbiddenException;
import br.com.unex.edutrack.mapper.task.TaskMapper;
import br.com.unex.edutrack.model.Subject;
import br.com.unex.edutrack.model.Task;
import br.com.unex.edutrack.model.User;
import br.com.unex.edutrack.repository.SubjectRepository;
import br.com.unex.edutrack.repository.TaskRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final SubjectRepository subjectRepository;
    private final UserService userService;

    public TaskService(TaskRepository taskRepository, TaskMapper taskMapper, SubjectRepository subjectRepository, UserService userService) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
        this.subjectRepository = subjectRepository;
        this.userService = userService;
    }

    public TaskResponseDto saveTask(int subjectId,TaskRequestDto data) {

        User user = userService.getAuthenticatedUser();

        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new EntityNotFoundException("Disciplina não encontrada com o ID: " + subjectId));
        if (subject.getUser().getId() != user.getId()) {
            throw new ForbiddenException("Você não tem permissão para visualizar esta disciplina");
        }

        Task task = taskMapper.toTask(data);
        task.setSubject(subject);
        Task persistedTask = taskRepository.save(task);
        return taskMapper.toTaskResponseDto(persistedTask);
    }
}

package br.com.unex.edutrack.service;

import br.com.unex.edutrack.dto.task.TaskResponseDto;
import br.com.unex.edutrack.model.Subject;
import br.com.unex.edutrack.model.Task;
import br.com.unex.edutrack.repository.SubjectRepository;
import br.com.unex.edutrack.repository.TaskRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final SubjectRepository subjectRepository;

    public TaskResponseDto getTaskDetails(Integer subjectId, Integer taskId, Integer userId) {

        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new EntityNotFoundException("Disciplina não encontrada"));

        if (!subject.getUser().getId().equals(userId)) {
            throw new EntityNotFoundException("Disciplina não pertence ao usuário");
        }

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Tarefa não encontrada"));

        if (!task.getSubject().getId().equals(subjectId)) {
            throw new EntityNotFoundException("A tarefa não pertence à disciplina informada");
        }

        return TaskResponseDto.fromEntity(task);
    }
}

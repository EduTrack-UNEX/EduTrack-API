package br.com.unex.edutrack.service;

import br.com.unex.edutrack.model.Subject;
import br.com.unex.edutrack.model.Task;
import br.com.unex.edutrack.model.User;
import br.com.unex.edutrack.repository.SubjectRepository;
import br.com.unex.edutrack.repository.TaskRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final SubjectRepository subjectRepository;

    public TaskService(TaskRepository taskRepository, SubjectRepository subjectRepository) {
        this.taskRepository = taskRepository;
        this.subjectRepository = subjectRepository;
    }

    private User getAuthenticatedUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Transactional
    public void deleteTask(int subjectId, int taskId) {
        User user = getAuthenticatedUser();

        Subject subject = subjectRepository.findByIdAndUser(subjectId, user)
                .orElseThrow(() -> new EntityNotFoundException("Disciplina não encontrada."));

        Task task = taskRepository.findByIdAndSubject(taskId, subject)
                .orElseThrow(() -> new EntityNotFoundException("Tarefa não encontrada nesta disciplina."));

        taskRepository.delete(task);

        List<Task> remainingTasks = taskRepository.findAllBySubject(subject);

        float average = 0;
        int progress = 0;

        if (!remainingTasks.isEmpty()) {
            long completedCount = remainingTasks.stream().filter(Task::isCompleted).count();
            progress = (int) ((completedCount * 100) / remainingTasks.size());

            var gradedTasks = remainingTasks.stream()
                    .filter(t -> t.getGrade() != null)
                    .mapToDouble(Task::getGrade)
                    .toArray();

            if (gradedTasks.length > 0) {
                double sum = 0;
                for (double g : gradedTasks) sum += g;
                average = (float) (sum / gradedTasks.length);
            }
        }

        subject.setAverage(average);
        subject.setProgress(progress);
        subjectRepository.save(subject);
    }
}

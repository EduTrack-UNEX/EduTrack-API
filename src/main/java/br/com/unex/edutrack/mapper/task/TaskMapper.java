package br.com.unex.edutrack.mapper.task;

import br.com.unex.edutrack.dto.task.TaskRequestDto;
import br.com.unex.edutrack.dto.task.TaskResponseDto;
import br.com.unex.edutrack.model.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    public Task toTask(TaskRequestDto data) {
        return new Task.TaskBuilder()
                .withName(data.name())
                .withGrade(data.grade())
                .withDueData(data.dueDate())
                .build();
    }

    public TaskResponseDto toTaskResponseDto(Task data) {
        return new TaskResponseDto(
                data.getId(),
                data.getName(),
                data.getGrade(),
                data.isCompleted(),
                data.getDueDate(),
                data.getCreatedAt(),
                data.getUpdatedAt()
        );
    }
}

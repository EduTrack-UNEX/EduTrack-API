package br.com.unex.edutrack.mapper.subject;

import br.com.unex.edutrack.dto.subject.SubjectRequestDto;
import br.com.unex.edutrack.dto.subject.SubjectResponseDto;
import br.com.unex.edutrack.model.Subject;
import org.springframework.stereotype.Component;

@Component
public class SubjectMapper {

    public Subject toSubject(SubjectRequestDto data){
        return new Subject.SubjectBuilder()
                .withName(data.name())
                .build();
    }

    public SubjectResponseDto toSubjectResponseDto(Subject data) {
        return new SubjectResponseDto(
                data.getId(),
                data.getName(),
                data.getCreatedAt(),
                data.getUpdatedAt()
        );
    }
}

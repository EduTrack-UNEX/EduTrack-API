package br.com.unex.edutrack.repository;

import br.com.unex.edutrack.model.Task;
import br.com.unex.edutrack.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

    List<Task> findAllBySubject(Subject subject);

    Optional<Task> findByIdAndSubject(int id, Subject subject);
}

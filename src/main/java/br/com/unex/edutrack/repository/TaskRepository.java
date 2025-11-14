package br.com.unex.edutrack.repository;

import br.com.unex.edutrack.model.Subject;
import br.com.unex.edutrack.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task,Integer> {

    Page<Task> findBySubjectOrderByIsCompletedAscIdAsc(Subject subject, Pageable pageable);
}

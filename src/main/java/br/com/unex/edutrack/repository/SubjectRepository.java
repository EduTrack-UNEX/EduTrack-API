package br.com.unex.edutrack.repository;

import br.com.unex.edutrack.model.Subject;
import br.com.unex.edutrack.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject,Integer> {

    List<Subject> findAllByUser(User user);

}

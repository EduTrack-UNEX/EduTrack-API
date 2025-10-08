package br.com.unex.edutrack.repository;

import br.com.unex.edutrack.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer>{
}

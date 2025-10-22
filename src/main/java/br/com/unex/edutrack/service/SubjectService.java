package br.com.unex.edutrack.service;

import br.com.unex.edutrack.dto.subject.SubjectRequestDto;
import br.com.unex.edutrack.dto.subject.SubjectResponseDto;
import br.com.unex.edutrack.mapper.subject.SubjectMapper;

import br.com.unex.edutrack.model.Subject;
import br.com.unex.edutrack.model.User;
import br.com.unex.edutrack.repository.SubjectRepository;
import br.com.unex.edutrack.repository.UserRepository;
import br.com.unex.edutrack.security.TokenService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final UserRepository userRepository;
    private final SubjectMapper subjectMapper;
    private final TokenService tokenService;

    public SubjectService(SubjectRepository subjectRepository, UserRepository userRepository, SubjectMapper subjectMapper, TokenService tokenService) {
        this.subjectRepository = subjectRepository;
        this.userRepository = userRepository;
        this.subjectMapper = subjectMapper;
        this.tokenService = tokenService;
    }

    public SubjectResponseDto saveSubject(SubjectRequestDto data){


        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Subject subject = subjectMapper.toSubject(data);
        subject.setUser(user);
        Subject persistedSubject = subjectRepository.save(subject);

        return subjectMapper.toSubjectResponseDto(persistedSubject);
    }

    private void validatedToken(String token){

        String emailUser = tokenService.validateToken(token);
        Optional<User> user = userRepository.findByEmail(emailUser);
        System.out.println(user);
    }
}

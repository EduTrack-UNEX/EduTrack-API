package br.com.unex.edutrack.security;


import br.com.unex.edutrack.exception.UnauthorizedException;
import br.com.unex.edutrack.model.User;
import br.com.unex.edutrack.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import java.io.IOException;


public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final HandlerExceptionResolver handlerExceptionResolver;

    public JwtAuthenticationFilter(TokenService tokenService, UserRepository userRepository, HandlerExceptionResolver handlerExceptionResolver) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
        this.handlerExceptionResolver = handlerExceptionResolver;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getServletPath();

        if (path.startsWith("/swagger-ui")
                || path.startsWith("/v3/api-docs")
                || path.startsWith("/actuator")
                || path.startsWith("/v1/users")
                || path.startsWith("/error")) {
            filterChain.doFilter(request, response);
            return;
        }


        try {
            String authHeader = request.getHeader("Authorization");

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                throw new UnauthorizedException("Acesso não autorizado. Token ausente ou inválido.");
            }

            String token = authHeader.replace("Bearer ","").trim();
            String userEmail = tokenService.validateToken(token);

            if (userEmail == null || userEmail.isEmpty()) {
                throw new UnauthorizedException("Acesso não autorizado. Token inválido ou expirado.");
            }

            User user = userRepository.findByEmail(userEmail).orElse(null);
            if (user == null) {
                throw new UnauthorizedException("Usuário não encontrado para o token fornecido.");
            }


            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response); // Continua para o controller

        } catch (UnauthorizedException e) {

            this.handlerExceptionResolver.resolveException(request, response, null, e);
        }
    }
}
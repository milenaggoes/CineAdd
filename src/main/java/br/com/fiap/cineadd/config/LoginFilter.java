package br.com.fiap.cineadd.config;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.fiap.cineadd.user.User;
import br.com.fiap.cineadd.user.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LoginFilter extends OncePerRequestFilter {

    UserRepository repository;

    public LoginFilter(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

                if (authentication != null){
                    var principal = (OAuth2User) authentication.getPrincipal();
                    var optional = repository.findById(Long.valueOf(principal.getName()));
                    if (optional.isEmpty()){
                        repository.save(User.convert(principal));
                    }
                }

                filterChain.doFilter(request, response);

    }



}
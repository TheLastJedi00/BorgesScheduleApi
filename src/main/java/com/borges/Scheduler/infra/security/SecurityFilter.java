package com.borges.Scheduler.infra.security;

import com.borges.Scheduler.repository.AdminRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private AdminRepository repository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("Filtro chamado");
        var tokenJWT = catchToken(request);


            if (tokenJWT != null) {
                System.out.println("Token existente");
                var subject = tokenService.getSubject(tokenJWT);
                System.out.println("Administrador requisitado: " + subject);
                var admin = repository.findByEmail(subject);
                System.out.println(admin);

//            var authentication = new UsernamePasswordAuthenticationToken(admin, null, admin.getAuthorities());
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//            System.out.println("Logado nesta requisição");

                try {
                if (admin != null) {
                    System.out.println("Admin encontrado");
                    var authentication = new UsernamePasswordAuthenticationToken(admin, null, admin.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    System.out.println("Logado nesta requisição");
                }} catch (Exception e){
                    logger.error("Falha ao processar token: {}" + e.getMessage());
                }
            }

        filterChain.doFilter(request, response);
    }



    private String catchToken(HttpServletRequest request) {
        System.out.println("Token recuperado");
        var authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "").trim();
        }

        return null;
    }
}

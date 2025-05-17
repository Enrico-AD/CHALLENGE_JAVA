package br.com.mottu.configuration.security;


import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import br.com.mottu.dto.UsuarioDto;
import br.com.mottu.model.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class LoginHandler implements AuthenticationSuccessHandler, AuthenticationFailureHandler {


    @Autowired
    private SessionManager sessionManager;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Usuario usuario = (Usuario) authentication.getPrincipal();


       /* if (usuario.getStatus() != null && (usuario.getStatus() == Status.PENDENTE || usuario.getStatus() == Status.INATIVO)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            String origin = request.getHeader("Origin");

            // Configura o cabeçalho Access-Control-Allow-Origin com a origem da solicitação
            //response.setHeader("Access-Control-Allow-Origin", origin);
            // response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
            response.setHeader("Access-Control-Allow-Origin", "https://app.geracaoalphaoficial.com.br");
            // Outros cabeçalhos CORS
            response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            String errorMessage = new Gson().toJson("Usuário com status pendente ou inativo. Não é permitido logar.");
            response.getWriter().write(errorMessage);
        } else {*/
            // Obter a sessão e o cookie JSESSIONID
            HttpSession session = request.getSession(false);
            if (session != null) {
                String sessionId = session.getId();
                Cookie jsessionidCookie = new Cookie("JSESSIONID", sessionId);

                // Configurar o cookie JSESSIONID com as propriedades desejadas
                jsessionidCookie.setPath("/");
                jsessionidCookie.setHttpOnly(true);
                jsessionidCookie.setSecure(true); // Definir como Secure para HTTPS
                jsessionidCookie.setMaxAge(3600); // Duração em segundos (1 hora)

                // Definir SameSite manualmente para 'Strict' ou 'Lax' conforme necessário
                response.setHeader("Set-Cookie", String.format("%s=%s; Path=%s; HttpOnly; SameSite=Strict; Secure",
                        jsessionidCookie.getName(), jsessionidCookie.getValue(), jsessionidCookie.getPath()));


                response.addCookie(jsessionidCookie);
                sessionManager.associateUser(sessionId, usuario.getUsername());
            }


            // Enviar informações do usuário no corpo da resposta
            // String jsonStr = new Gson().toJson(new ResponseUsuarioDTO(usuario));
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            PrintWriter out = response.getWriter();
            //   out.print(jsonStr);
            out.flush();
        //}
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        System.out.println("onAuthenticationFailure()");
        String origin = request.getHeader("Origin");

        // Configura o cabeçalho Access-Control-Allow-Origin com a origem da solicitação
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");

        // Outros cabeçalhos CORS
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");
        response.setHeader("Access-Control-Allow-Credentials", "true");

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}

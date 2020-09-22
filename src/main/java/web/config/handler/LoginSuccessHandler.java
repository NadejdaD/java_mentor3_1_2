package web.config.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.function.Predicate;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException, ServletException {

        if (authentication.getAuthorities().stream()
                .allMatch((Predicate<GrantedAuthority>) grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_USER"))) {
            httpServletResponse.sendRedirect("/user");
        } else if (authentication.getAuthorities().stream()
                .anyMatch((Predicate<GrantedAuthority>) grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
            httpServletResponse.sendRedirect("/admin/home");
        }
    }
}
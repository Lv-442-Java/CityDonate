package exeption;

import org.springframework.security.core.AuthenticationException;

public class JwtAuthenticationExeption extends AuthenticationException {
    public JwtAuthenticationExeption(String msg, Throwable t) {
        super(msg, t);
    }

    public JwtAuthenticationExeption(String msg) {
        super(msg);
    }
}

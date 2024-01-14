package sbankpaymentms.util;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import sbankpaymentms.jwt.JwtService;
import sbankpaymentms.model.CustomAuthenticationToken;
import sbankpaymentms.model.UserClaims;

import java.io.IOException;
import java.util.Optional;

@AllArgsConstructor
@Slf4j
public class AuthRequestFilter extends OncePerRequestFilter {

    private static final String TOKEN_PREFIX = "Bearer ";

    private JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        getAuthentication(request).ifPresent(auth -> SecurityContextHolder.getContext().setAuthentication(auth));
        filterChain.doFilter(request, response);
    }

    public Optional<Authentication> getAuthentication(HttpServletRequest req) {
        return Optional.ofNullable(req.getHeader(HttpHeaderConstant.AUTHORIZATION))
                .filter(this::isBearerAuth)
                .flatMap(this::getAuthenticationBearer);
    }

    private boolean isBearerAuth(String header) {
        return header.startsWith(TOKEN_PREFIX);
    }

    private Optional<Authentication> getAuthenticationBearer(String header) {
        String token = header.substring(TOKEN_PREFIX.length()).trim();
        Claims claims = jwtService.parseToken(token);
        log.info("The claims parsed {}", claims);
        return Optional.of(getAuthenticationBearer(claims));
    }

    private Authentication getAuthenticationBearer(Claims claims) {
        UserClaims userClaims = UserClaims.builder()
                .id(Long.valueOf(claims.getId()))
                .cif(claims.getSubject())
                .build();
        return new CustomAuthenticationToken(userClaims);
    }

}

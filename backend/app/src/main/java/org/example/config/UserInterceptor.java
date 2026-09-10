package org.example.config;

import java.security.Key;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Interceptor Spring MVC per verificare l'autenticazione generica degli utenti.
 * Valida la presenza e l'integrità del token JWT per gli endpoint protetti
 */

@Component
public class UserInterceptor implements HandlerInterceptor{
    private final Key SECRET_KEY = Keys.hmacShaKeyFor("ChiaveSegretaPerIlControlloSicurezza".getBytes());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        if("OPTIONS".equalsIgnoreCase(request.getMethod())){
            return true;
        }

        String authHeader = request.getHeader("Authorization");

        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Accesso negato: token mancante o non valido.");
            return false;
        }
        String token = authHeader.substring(7);

        try {
            // Decodifica e verifica di validità/scadenza del JWT
            Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token);

            return true; // Token valido e autentico (sia per USER che per ADMIN)

        } catch (Exception e) {
            // Token scaduto, contraffatto o malformato
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Accesso negato: token scaduto o non valido.");
            return false;
        }
    }
}

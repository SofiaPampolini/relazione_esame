package org.example.config;

import java.security.Key;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Interceptor Spring MVC per la gestione degli endpoint riservati agli amministratori
 */

@Component
public class AdminInterceptor implements HandlerInterceptor {
    // Definizione chiave segreta condivisa per la verifica e decodifica della firma del token JVT
    private final Key SECRET_KEY = Keys.hmacShaKeyFor("ChiaveSegretaPerIlControlloSicurezza".getBytes());

    /** 
     * Metodo eseguito prima dell'inoltro della richiesta HTTP al controller corrispondente.
     * Ritorna 'true' per consentire la richiesta, 'false' per bloccarla
    */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        // Se il metodo è OPTIONS la richiesta passa automaticamente
        if("OPTIONS".equalsIgnoreCase(request.getMethod())){
            return true;
        }

        // Estrazione header "Authorization" inviato dal client
        String authHeader = request.getHeader("Authorization");

        // Controllo presenza e formato header: deve esistere e iniziare con il prefisso "Bearer"
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("Accesso riservato agli amministratori.");
            return false;
        }

        // Esteazione della stringa del token rimuovendo i primi 7 caratteri ("Bearer")
        String token = authHeader.substring(7);
        
        try {
            // Decodifica, verifica crittografica e parsing del payload del JWT
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)          // Imposta chiave per verificare la firma
                    .build()
                    .parseClaimsJws(token)              // Valida la firma e controlla l'eventuale scadenza
                    .getBody();                         // Estrae il corpo contenente i claims del token

            // Estrazione claim personalizzato "ruolo"
            String ruolo = claims.get("ruolo", String.class);

            // Verifica autorizzazione per ruolo ADMIN
            if (!"ADMIN".equals(ruolo)) {
                // Se l'utente non ha i privilegi di amministratore, blocca l'accesso
                response.setStatus(HttpServletResponse.SC_FORBIDDEN); 
                response.getWriter().write("Accesso riservato agli amministratori.");
                return false;
            }

            // Autenticazione e autorizzazione completate con successo, la richiesta viene inviata al controller
            return true;

        } catch (Exception e) {
            // Token scaduto, manomesso o non valido
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token non valido o scaduto.");
            return false;
        }
    }
}

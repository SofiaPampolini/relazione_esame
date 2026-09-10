package org.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.List;

/**
 * Classe di configurazione dell'applicazione basata su Spring Security.
 * Configura la catena dei filtri di sicurezza HTTP e le politiche di abilitazione delle chiamare CORS.
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    // Catena di filtri di sicurezza
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Disabilita la protezione CSRF (Cross-Site Request Forgery)
            .csrf(AbstractHttpConfigurer::disable)

            // Associa la configurazione delle regole CORS definita nel Bean corsConfigurationSource
            .cors(cors->cors.configurationSource(corsConfigurationSource()))

            // Disabilita il form di login HTML automatico
            .formLogin(AbstractHttpConfigurer::disable)

            // Disabilita l'autenticazione HTTP Basic
            .httpBasic(AbstractHttpConfigurer::disable)

            // Configura l'autorizzazione per le rotte HTTP
            .authorizeHttpRequests(auth -> auth
                // Consente l'accesso a qualsiasi endpoint delegando la sicurezza e la verifica dei JWT agli Interceptor
                .anyRequest().permitAll()
            );

        return http.build();
    }

    // Definizione delle politiche CORS per permettere le chiamate provenienti da porte differenti
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        // Autorizza solo le richieste provenienti dal client frontend
        config.setAllowedOrigins(List.of("http://localhost:4200"));

        // Specifica i metodi HTTP consentiti
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // Permette tutti gli header nelle richieste HTTP
        config.setAllowedHeaders(List.of("*"));

        // Abilita l'invio di credenziali nelle richieste CORS
        config.setAllowCredentials(true);

        // Applica la configurazione CORS a tutti i percorsi dell'applicazione
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
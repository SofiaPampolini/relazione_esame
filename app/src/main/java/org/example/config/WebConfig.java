package org.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Classe di configurazione Web MVC per l'applicazione.
 * Implementa 'WebMvcConfigurer' per la registrazione degli interceptor e la loro associazione ai relativi percorsi delle API.
 */

@Configuration
public class WebConfig implements WebMvcConfigurer{
    // Iniezione del componente AdminInterceptor per la protezione delle rotte amministrative
    @Autowired
    private AdminInterceptor adminInterceptor;

    // Iniezione del componente UserInterceptor per la verifica dei token di autenticazione utente
    @Autowired
    private UserInterceptor userInterceptor;

    // Metodo di callback per aggiungere e configurare gli interceptor
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        // Registrazione AdminInterceptor: applicazione dei controlli di sicurezza esclusivi per gli amministratori
        registry.addInterceptor(adminInterceptor)
                .addPathPatterns("/api/admin/**");

        /**
         * Registrazione UserInterceptor: protezione percorsi specificati tramite richiamo del controllo sul token per le risorse riservate.
         * Esclusione dai controlli di autenticazione degli endpoint pubblici accessibili senza token
         */
        registry.addInterceptor(userInterceptor)
                .addPathPatterns("/api/prenotazioni/**", "/api/utenti/**, /api/notifiche/**")
                .excludePathPatterns("/api/utenti/login", "/api/utenti/registrazione");
    }
}

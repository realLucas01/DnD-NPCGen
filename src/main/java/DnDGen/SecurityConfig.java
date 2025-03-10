package DnDGen;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author Marie
 */

@Configuration
public class SecurityConfig {

    /**
     * Konfiguriert und gibt die Spring Security Filterkette für die Anwendung zurück.
     * Diese Methode richtet Autorisierung-, Authentifizierung-, Login- und Logout-Mechanismen ein.
     *
     * @param http die {@link HttpSecurity} Instanz, die verwendet wird, um die Sicherheitseinstellungen zu konfigurieren.
     * @return eine konfigurierte {@link SecurityFilterChain} Instanz für die Anwendung.
     * @throws Exception wenn ein Fehler bei der Konfiguration der {@link SecurityFilterChain} auftritt.
     * 
     * @author chatGPT
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/generator").authenticated() // /generator nur für eingeloggte Nutzer
                        .anyRequest().permitAll()
                )
                .formLogin(login -> login
                        .loginPage("/login") // Standard-Login-Formular von Spring Security
                        .defaultSuccessUrl("/generator", true) // Nach Login weiter zu /generator
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout") // Nach Logout zurück zur Login-Seite
                        .permitAll()
                )
                .sessionManagement(session -> session
                        // Optional: Konfiguration von Session-Timeout
                        .invalidSessionUrl("/login") // Leitet bei ungültiger Session zur Login-Seite
                );


        return http.build();
    }

    /**
     * Konfiguriert und gibt eine {@link UserDetailsService} Implementierung zurück,
     * um die Benutzer-Authentifizierung zu verwalten.
     * Diese Methode definiert einen In-Memory-Benutzer mit dem Benutzernamen "admin",
     * dem Passwort "123" und der Rolle "ADMIN".
     *
     * @return eine konfigurierte {@link UserDetailsService} Instanz, die das Management
     * von Benutzerdaten im Speicher bereitstellt.
     * @author chatGPT
     */
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("123")
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(admin);
    }
}


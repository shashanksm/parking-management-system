package io.shashanksm.pms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.security.web.SecurityFilterChain;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                // Public access for the home page or specific login routes
                .requestMatchers("/", "/login**", "/oauth2/**").permitAll() 
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                .requestMatchers("/api/v1/**").hasAnyRole("USER", "ADMIN")
                .anyRequest().authenticated()
            )
            .oauth2Login(oauth2 -> oauth2
                .userInfoEndpoint(userInfo -> userInfo
                    .userAuthoritiesMapper(this.userAuthoritiesMapper())
                )
            )
            .logout(logout -> logout.logoutSuccessUrl("/").permitAll())
            .csrf(csrf -> csrf.disable()); // Disable for simplicity, not recommended for stateful web apps.

        return http.build();
    }

    private GrantedAuthoritiesMapper userAuthoritiesMapper() {
        return (authorities) -> {
            Set<GrantedAuthority> mappedAuthorities = new HashSet<>();

            authorities.forEach(authority -> {
                if (authority instanceof OidcUserAuthority oidcUserAuthority) {
                    Map<String, Object> claims = oidcUserAuthority.getAttributes();
                    if (claims.containsKey("email")) {
                        String email = (String) claims.get("email");
                        if (email != null && email.equals("shashank.mutgi@gmail.com")) {
                            mappedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                        } else {
                            mappedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
                        }
                    }
                } else if (authority instanceof OAuth2UserAuthority oauth2UserAuthority) {
                    Map<String, Object> userAttributes = oauth2UserAuthority.getAttributes();
                    if (userAttributes.containsKey("email")) {
                        String email = (String) userAttributes.get("email");
                        if (email != null && email.equals("shashank.mutgi@gmail.com")) {
                            mappedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                        } else {
                            mappedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
                        }
                    }
                }
            });
            return mappedAuthorities;
        };
    }
}
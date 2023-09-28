package com.example.cinemaapp.Configuration;

import org.modelmapper.ModelMapper;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

@Configuration
public class SecurityConfiguration {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity security) throws Exception {
        security.csrf(AbstractHttpConfigurer::disable);
        security.authorizeHttpRequests(s -> {
            try {
                s.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll().requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll()
                .requestMatchers("/", "/login","/register","/login_failed").permitAll()
                .anyRequest().authenticated().and().formLogin(login -> login
                .loginPage("/login")
                .usernameParameter("email")
                .passwordParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY)
                .defaultSuccessUrl("/",true)
                .failureForwardUrl("/login_failed"))
                .logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/")
                .deleteCookies("JSESSIONID").clearAuthentication(true).invalidateHttpSession(true));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        return security.build();
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityContextRepository securityRepository() {
        return new DelegatingSecurityContextRepository(
        new RequestAttributeSecurityContextRepository(),
        new HttpSessionSecurityContextRepository());
    }

    @Bean
    public ModelMapper mapper() {
        return new ModelMapper();
    }
}

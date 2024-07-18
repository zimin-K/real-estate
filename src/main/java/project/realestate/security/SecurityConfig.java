package project.realestate.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomAuthenticationSuccessHandler successHandler;

    public SecurityConfig(CustomAuthenticationSuccessHandler successHandler) {
        this.successHandler = successHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler) throws Exception{
        http
                .authorizeHttpRequests(authorize -> authorize
                                .requestMatchers("/", "/sign/**", "/board/main", "/board/detail/**", "/css/**", "/images/**").permitAll()
                                .requestMatchers("/board/register", "/board/update/**").hasAuthority("ADMIN")
                                .anyRequest().authenticated()
                                // .hasRole("ADMIN")
                )
                //.csrf(csrf -> csrf.disable())

                // login 설정
                .formLogin(form -> form
                        .loginPage("/sign/login")
                        //.defaultSuccessUrl("/", true)
                        .successHandler(customAuthenticationSuccessHandler)
                        .failureUrl("/sign/login?error=true")
                        .usernameParameter("email") // default : username -> email 로 변경
                        .passwordParameter("password")
                        //.loginProcessingUrl("/auth") // POST 요청
                        .permitAll()
                )

                // logout 설정
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                );

        return http.build();

    }

}

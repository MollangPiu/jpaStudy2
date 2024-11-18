package kr.soft.campus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrfConfig -> csrfConfig.disable()) // CSRF 비활성화
                .headers(headerConfig -> headerConfig.frameOptions().disable()) // Frame 옵션 비활성화
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers(PathRequest.toH2Console()).permitAll() // H2 콘솔 접근 허용
                        .requestMatchers("/", "/login", "/login-error").permitAll() // 기본 및 로그인 경로 접근 허용
                        .requestMatchers("/user/**").hasAuthority("ROLE_USER") // USER 권한 요구
                        .requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN") // ADMIN 권한 요구
                        .anyRequest().authenticated()) // 나머지 요청은 인증 필요
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .failureUrl("/login-error")
                        .defaultSuccessUrl("/home", true))
                .exceptionHandling(exceptionConfig -> exceptionConfig
                        .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"))
                        .accessDeniedHandler(new AccessDeniedHandlerImpl()));

        return http.build();
    }
}

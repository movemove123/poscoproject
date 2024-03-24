package com.posco.poscoproject.config;

import com.posco.poscoproject.service.BranchService;
import com.posco.poscoproject.service.LoginService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
//@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //원본
    private final LoginService loginService;

    /* 로그인 실패 핸들러 의존성 주입 */
    private final CustomFailureHandler customFailureHandler;

    private final CustomAuthenticationSuccessHandler successHandler;

    public SecurityConfig(LoginService loginService, CustomFailureHandler customFailureHandler, @Lazy CustomAuthenticationSuccessHandler successHandler) {
        this.loginService = loginService;
        this.customFailureHandler = customFailureHandler;
        this.successHandler = successHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler(BranchService branchService) {
        return new CustomAuthenticationSuccessHandler(branchService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()

                 //테스트
                .loginPage("/").permitAll()
                .loginProcessingUrl("/testLogin")
                .successHandler(successHandler)
//                .failureUrl("/testLogin/error").permitAll()
                .failureHandler(customFailureHandler) // 로그인 실패 핸들러
                .defaultSuccessUrl("/main")
                .usernameParameter("email")
                .passwordParameter("password")
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")
        ;

        http.authorizeRequests()
                .mvcMatchers("/fontawesome-free/**", "/css/**", "/js/**", "/img/**").permitAll()
                .mvcMatchers("/", "/login").permitAll()
                .mvcMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
        ;

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(loginService).passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/fontawesome-free/**", "/favicon.ico","/error");
    }
}

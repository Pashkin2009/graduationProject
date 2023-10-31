package ru.pavel.graduationProject.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.pavel.graduationProject.services.PersonDetailsService;


@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PersonDetailsService personDetailsService;

    @Autowired
    public SecurityConfig(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }
    @Override
    protected void configure(HttpSecurity  httpSecurity) throws Exception{
    //Конфигурация авторизации
        httpSecurity.authorizeRequests()
                .antMatchers("/admin","/admin/**").hasAnyRole("ADMIN")//Tолько администратор
                .antMatchers("/authorization/login","/error").permitAll()//доступны не аторизированным пользователям
                .anyRequest().hasAnyRole("USER","ADMIN")
                .and()
                .formLogin()
                .loginPage("/authorization/login")
                .loginProcessingUrl("/login_process")
                .defaultSuccessUrl("/main",true)
                .failureUrl("/authorization/login?error")
                .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/authorization/login");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(personDetailsService).passwordEncoder(getPasswordEncoder());
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new  BCryptPasswordEncoder();
    }
}

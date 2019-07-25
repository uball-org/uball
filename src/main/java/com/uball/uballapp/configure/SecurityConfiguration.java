package com.uball.uballapp.configure;

import com.uball.uballapp.services.UserDetailsLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private UserDetailsLoader usersLoader;

    public SecurityConfiguration(UserDetailsLoader usersLoader) {
        this.usersLoader = usersLoader;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(usersLoader) // How to find users by their username
                .passwordEncoder(passwordEncoder()) // How to encode and verify passwords
        ;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                /* Login configuration */
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/userprofile") // user's home page, it can be any URL
                .permitAll() // Anyone can go to the login page
                /* Logout configuration */
                .and()
                .logout()
//                **********
                .logoutSuccessUrl("/login?logout") // append a query string value
//                ***********

                /* Pages that can be viewed without having to log in */
                .and()
                .authorizeRequests()
                // anyone can see the home, register and login page
                .antMatchers("/", "/register", "/login")
                .permitAll()
                /* Pages that require athentication */
                .and()
                .authorizeRequests()
                .antMatchers(


                        "/userprofile/{id}",
                        "/userprofile",
                        "/admindashboard",
                        "/edituser/{id}",
                        "/weeks-scores",
                        "/groups/create",
                        "/groups/{id}/edit",
                        "/groups/{id}/delete",
                        "/machines/create",
                        "/machines/{id}/edit",
                        "/machines/{id}/delete",
                        "/leagues/create",
                        "/leagues/{id}/edit",
                        "/leagues/{id}/delete",
                        "/leagues",
                        "/about"
                )
                .authenticated()
        ;
    }
}

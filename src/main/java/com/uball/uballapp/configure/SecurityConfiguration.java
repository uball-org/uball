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
                .loginPage("/")
                .defaultSuccessUrl("/") // user's home page, it can be any URL
                .permitAll() // Anyone can go to the login page
                /* Logout configuration */
                .and()
                .logout()
//                **********
//                I do not think this will work?
                .logoutSuccessUrl("/?logout") // append a query string value
//                ***********

                /* Pages that can be viewed without having to log in */
                .and()
                .authorizeRequests()
                .antMatchers("/") // anyone can see the home and the ads pages
                .permitAll()
                /* Pages that require athentication */
                .and()
                .authorizeRequests()
                .antMatchers(
                        "/user/userprofile", // only authenticated users can create ads
                        "/admin/admindashboard" // only authenticated users can create ads
//                        "/user/{id}/edit", // only authenticated users can edit ads
//                        "/user/{id}/disable"// only authenticated users can delete ads
                )
                .authenticated()
        ;
    }
}

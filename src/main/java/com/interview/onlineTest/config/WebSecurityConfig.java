package com.interview.onlineTest.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
@PropertySource(value = { "classpath:application.properties"})
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/index").hasRole("USER")
                .antMatchers(HttpMethod.GET, "/admin").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/users").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/countUsers").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/countUsersTested").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/countUsersTestedAllQuestions").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/countUsersTestedAllQuestionsSuccess").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/answers").hasRole("USER")
                .antMatchers(HttpMethod.GET, "/queries").hasRole("USER")
                .antMatchers(HttpMethod.POST, "/queries").hasRole("ADMIN")
                .antMatchers("/", "/home","/registration","/users").permitAll()
                .antMatchers(HttpMethod.POST,"/users").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/js/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().disable();
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery("select username, password, enabled"
                        + " from users where username=?")
                .authoritiesByUsernameQuery("select u.username, ur.authority "
                        +  "from users u inner join authorities ur on u.id = ur.user_id where u.username=?")
                .passwordEncoder(new BCryptPasswordEncoder());
    }


}

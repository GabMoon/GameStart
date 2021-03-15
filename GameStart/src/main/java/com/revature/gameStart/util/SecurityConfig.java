//package com.revature.gameStart.util;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
//import javax.sql.DataSource;
//import java.net.PasswordAuthentication;
//
//
//@EnableWebSecurity
////@EnableGlobalMethodSecurity(securedEnabled = true)
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Bean
//    public BCryptPasswordEncoder passwordEncoder() {
//        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//        return bCryptPasswordEncoder;
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                            .antMatchers("/users/authentication","/users/register").permitAll()
//                            .antMatchers("/")
//                            .anyRequest().authenticated()
//                            .and()
//                .
//    }
//
//
//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        http    .antMatcher("/**")
//                .authorizeRequests()
//                .anyRequest().hasAnyAuthority("Basic")
//                .and()
//                .formLogin();
//    }
//
//    @Autowired
//    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
////        auth.jdbcAuthentication()
////                .dataSource(dataSource)
////                .usersByUsernameQuery("select  email, password, enabled " +
////                                    "from app_user where username = ? ")
////                .authoritiesByUsernameQuery("select role_name from app_user " +
////                                    "where username = ? ");
//                auth.inMemoryAuthentication()
//                .withUser("user")
//                //By default it is assumed that the password is encrypted
//                        .password("{noop}pass") // Spring Security 5 requires specifying the password storage format
//                .roles("USER")
//                .authorities("Basic");
//    }
//}

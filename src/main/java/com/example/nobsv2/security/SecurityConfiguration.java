package com.example.nobsv2.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfiguration {

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class).build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                //Allows for POST, PUT, DELETE mappings with authentications
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> {
                    //have to let the user create new without valid credentials
                    authorize.requestMatchers("/createnewuser").permitAll();

                    //must be at the bottom (idk why)
                    authorize.anyRequest().authenticated();
                })
                .addFilterBefore(
                        new BasicAuthenticationFilter(authenticationManager(httpSecurity)),
                        UsernamePasswordAuthenticationFilter.class
                )
                .build();
    }
}

//                    authorize.requestMatchers("/open").permitAll();
//                    authorize.requestMatchers("/closed").authenticated();
//                    authorize.requestMatchers(HttpMethod.POST, "/product").authenticated();
//                    authorize.requestMatchers(HttpMethod.PUT, "/product/{id}").authenticated();
//                    authorize.requestMatchers(HttpMethod.GET, "/special").hasAuthority("SPECIAL");
//                    authorize.requestMatchers(HttpMethod.GET, "/basic").hasAnyAuthority("SPECIAL", "BASIC");

//    @Bean
//    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
//        UserDetails admin = User
//                .withUsername("admin")
//                .authorities("BASIC", "SPECIAL")
//                .roles("superuser")
//                .password(encoder.encode("1")) //Spring Boot no nos dejará usar texto raw para el apartado de la contraseña (tiene que estar encriptada)
//                .build();
//
//        UserDetails user = User
//                .withUsername("user")
//                .authorities("BASIC")
//                .roles("basicuser")
//                .password(encoder.encode("2")) //Spring Boot no nos dejará usar texto raw para el apartado de la contraseña (tiene que estar encriptada)
//                .build();
//
//        return new InMemoryUserDetailsManager(admin,user);
//    }

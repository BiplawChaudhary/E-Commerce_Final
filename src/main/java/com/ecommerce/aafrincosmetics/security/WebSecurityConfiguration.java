package com.ecommerce.aafrincosmetics.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

@EnableWebSecurity
@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {


    @Bean
    public UserDetailsService userDetailsService(){
        return new UserDetailsServiceImpl();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth)throws Exception{
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http)throws Exception{
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/","/login", "/sign-up", "/search" , "/static/**", "/product/**", "/get-single-product/**").permitAll()
                .antMatchers("/css/**", "/js/**", "/adminPages/**", "adminPages/**", "/authAssets/**", "/uploads/**","/mainAssets/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login").permitAll()
                .defaultSuccessUrl("/", false)
                .successHandler((request, response, authentication) -> {
                    // Handle success authentication and redirect based on role
                    //**--------No need to send request if not going to redirect to the
                    //**------URL request came from
                    String targetUrl = determineTargetUrl(request,authentication);
                    response.sendRedirect(targetUrl);
                })
                .and()
                .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/")
                    .permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/403");
    }

    private String determineTargetUrl(HttpServletRequest request, Authentication authentication) {
        // Get the user's authorities/roles
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        // Check if the user has admin role
        boolean isAdmin = authorities.stream()
                .anyMatch(authority -> authority.getAuthority().equals("ADMIN"));

        // Determine the target URL based on the user's role
        if (isAdmin) {
            return "/admin/home";
        } else {
//            // Get the original request URL
//            SavedRequest savedRequest = new HttpSessionRequestCache().getRequest(request, null);
//            String originalUrl = savedRequest.getRedirectUrl();
//
//            // Check if the original URL exists and is not the login page
//            if (originalUrl != null && !originalUrl.equals("/login")) {
//                return originalUrl;
//            } else {
//                return "/";
//            }
//        }
            return "/";
        }
    }
}

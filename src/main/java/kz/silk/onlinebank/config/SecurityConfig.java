package kz.silk.onlinebank.config;

import kz.silk.onlinebank.controller.AuthController;
import kz.silk.onlinebank.controller.ProfileController;
import kz.silk.onlinebank.service.ProfileService;
import kz.silk.onlinebank.utils.jwt.AuthEntryPointJwt;
import kz.silk.onlinebank.utils.jwt.AuthTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Security configuration
 *
 * @author YermukhanJJ
 */
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final ProfileService profileService;

    private final AuthEntryPointJwt authEntryPointJwt;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public AuthTokenFilter authTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(profileService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);

        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(authEntryPointJwt)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests()
                .antMatchers(AuthController.CONTROLLER_PATH + "/**").permitAll()
                .antMatchers(ProfileController.CONTROLLER_PATH + "/edit-role", ProfileController.CONTROLLER_PATH + "/lock").hasAuthority("ADMIN")
                .anyRequest().authenticated();

        http.headers().frameOptions().sameOrigin();
        http.authenticationProvider(authenticationProvider());
        http.addFilterBefore(authTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}

package learn.amateur_hour.security;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtConverter converter;

    public SecurityConfig(JwtConverter converter) {
        this.converter = converter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.cors();

        http.authorizeRequests()
                .antMatchers("/api/authenticate").permitAll()
                .antMatchers("/api/create_account").permitAll()
             // all endpoints to protect go here
             //    .antMatchers(HttpMethod.GET, "/api/event/**", "api/rsvp/**", "/api/rating/**", "/api/tag/**", "/api/user/*").permitAll()
                .antMatchers(HttpMethod.GET, "/api/**", "/").permitAll()
                .antMatchers(HttpMethod.POST, "/api/event", "/api/rsvp", "/api/rating").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.POST, "/api/tag").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/event/*", "/api/rsvp/**", "/api/rating/*").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/tag/*").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/event/*", "/api/tag/*", "/api/rating/*", "/api/rsvp/**").hasAnyRole("USER", "ADMIN")
             // require authentication for any request...
                .anyRequest().authenticated()
                .and()
                .addFilter(new JwtRequestFilter(authenticationManager(), converter))
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }
}
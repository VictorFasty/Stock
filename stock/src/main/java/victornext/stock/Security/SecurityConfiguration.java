package victornext.stock.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import victornext.stock.Services.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(configurer -> configurer.loginPage("/login").permitAll())
                .authorizeHttpRequests(authorize -> {
                    authorize.requestMatchers("/users/**").permitAll();
                    authorize.requestMatchers("/register").permitAll();
                    authorize.requestMatchers("/product/delete/**").hasRole("ADMIN");
                    authorize.requestMatchers("/Enterprises/delete/**").hasRole("ADMIN");
                    authorize.requestMatchers("/product/**").hasAnyRole("USER", "ADMIN");
                    authorize.requestMatchers("/Enterprises/**").hasAnyRole("USER", "ADMIN");
                    authorize.anyRequest().authenticated();
                })
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public UserDetailsService userDetailsService(UserService service){
        return  new CustomUserDetailsService(service);
    }
}

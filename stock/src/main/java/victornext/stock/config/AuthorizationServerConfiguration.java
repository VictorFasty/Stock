package victornext.stock.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import victornext.stock.Security.CustomAuthentication;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class AuthorizationServerConfiguration {


    @Bean
    @Order(1)
    public SecurityFilterChain authServerSecurityFilterChain(HttpSecurity http) throws Exception {
        var authorizationServerConfigurer = new OAuth2AuthorizationServerConfigurer();

        http
                .securityMatcher(authorizationServerConfigurer.getEndpointsMatcher())
                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
                .csrf(csrf -> csrf.ignoringRequestMatchers(authorizationServerConfigurer.getEndpointsMatcher()))
                .apply(authorizationServerConfigurer)
                .oidc(withDefaults());

        http.oauth2ResourceServer(oauth2 -> oauth2.jwt());
        http.formLogin(configurer -> configurer.loginPage("/login"));

        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }


    @Bean
    public TokenSettings tokenSettings() {
        return TokenSettings.builder()
                .accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED)
                .accessTokenTimeToLive(Duration.ofMinutes(60))
                .refreshTokenTimeToLive(Duration.ofMinutes(90))
                .build();
    }

    @Bean
    public ClientSettings clientSettings() {
        return ClientSettings.builder()
                .requireAuthorizationConsent(false)
                .build();
    }


    @Bean
    //Pra gerar o token JWK(Json Web Key) é uma representação em Json pra uma chave criptografada
    public JWKSource<SecurityContext> jwkSource() throws Exception {
        RSAKey rsaKey = generateRSA();
        JWKSet jwkSet = new JWKSet(rsaKey);

        return new ImmutableJWKSet<>(jwkSet);
    }


    private RSAKey generateRSA() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();


        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();


        return new RSAKey
                .Builder(publicKey)
                .privateKey(privateKey)
                .keyID(UUID.randomUUID().toString())
                .build();
    }


    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }


    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder()
                .tokenEndpoint("/oauth2/token")
                .tokenIntrospectionEndpoint("/oauth/introspect")
                .tokenRevocationEndpoint("/oauth2/revoke")
                .authorizationEndpoint("/oauth2/authorize")
                .oidcUserInfoEndpoint("/oauth2/userinfo")
                .jwkSetEndpoint("/oauth2/jwks")
                .oidcLogoutEndpoint("/oauth2/logout")
                .build();
    }


    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> tokenCustomizer() {
        return context -> {
            var principal = context.getPrincipal();


            if (principal instanceof CustomAuthentication authentication) {
                OAuth2TokenType typeToken = context.getTokenType();

                if (OAuth2TokenType.ACCESS_TOKEN.equals(typeToken)) {

                    Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
                    List<String> authoritiesList = authorities.stream().map(GrantedAuthority::getAuthority).toList();


                    context
                            .getClaims()
                            .claim("authorities", authoritiesList)
                            .claim("email ", authentication.getUserModel().getEmail());

                }
            }
        };
    }
}

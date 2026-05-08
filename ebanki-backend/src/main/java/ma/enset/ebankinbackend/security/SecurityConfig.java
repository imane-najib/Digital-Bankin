package ma.enset.ebankinbackend.security;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.crypto.spec.SecretKeySpec;




@Configuration//Cette annotation indique que la classe est une classe de configuration Spring.
@EnableWebSecurity //Cette annotation active la sécurité web de Spring Security.
@EnableMethodSecurity(prePostEnabled = true) //sert à activer la sécurité au niveau des méthodes dans Spring Security.
public class SecurityConfig {

    @Value("${jwt.secret}")//cette annotation sert a récupérer une valeur d'environement et l'injecter dan sune variable
    private String secretKey;  //avec le quelle on signe le JWT //on va le declarer dans applications.propreties

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        PasswordEncoder passwordEncoder = passwordEncoder();
        return new InMemoryUserDetailsManager(
                User.withUsername("user1").password(passwordEncoder.encode("1234")).authorities("USER").build(),
                User.withUsername("admin").password(passwordEncoder.encode("1234")).authorities("USER", "ADMIN").build()
        );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //HttpSecurity est l’objet qui te permet de définir les règles de sécurité.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) {
        return httpSecurity
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))//l'authentification stateless
                .csrf(crsf -> crsf.disable())//puisque on utilse authentification statefull donc il faut desactiver crsf protection
                .cors(Customizer.withDefaults())//signifie Active CORS et utilise la configuration CORS que j’ai définie dans un bean (voir dessous)
                .authorizeHttpRequests(ar -> ar
                        .requestMatchers("/auth/login/**").permitAll()
                        .anyRequest().authenticated()
                )


                //toute les requetes necessite une authentification
                //.httpBasic(Customizer.withDefaults())//
                .oauth2ResourceServer(oa -> oa.jwt((Customizer.withDefaults())))
                .build();

    }


    // Crée un encodeur JWT qui génère et signe les tokens avec une clé secrète
    @Bean
    JwtEncoder jwtEncoder() {
        //String secretKey="9faa372517ac1d389758d3750fc07acf00f542277f26fec1ce4593e93f64e338";//il doit etre 64bytes
        return new NimbusJwtEncoder(new ImmutableSecret<>(secretKey.getBytes()));
    }

    // Crée un décodeur JWT qui vérifie la signature, l’expiration et extrait les informations du toke
    @Bean
    JwtDecoder jwtDecoder() {
        //String secretKey="9faa372517ac1d389758d3758fc07acf00f542277f26fec1ce4593e93f64e338";
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "RSA");
        return NimbusJwtDecoder.withSecretKey(secretKeySpec).macAlgorithm(MacAlgorithm.HS512).build();
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);//load user by username
        provider.setPasswordEncoder(passwordEncoder());
        // provider.setUserDetailsService(userDetailsService);///cette ligne a fait un erreur
        return new ProviderManager(provider);
    }
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();//Tu crées un objet qui contient les règles CORS
        corsConfiguration.addAllowedOrigin("*");//Autorise tous les domaines
        corsConfiguration.addAllowedMethod("*");//Autorise tous les methodes http put , delete, post ...
        corsConfiguration.addAllowedHeader("*");//Autorise tous les headers :
        //corsConfiguration.setExposedHeaders(List.of("x-auth-token"));//Permet au frontend de lire ce header qu'on envoie qui contient le token (voir on a deja lui a donner ce nom)
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();//un objet source:C’est un conteneur de configurations CORS liées à des URLs
        source.registerCorsConfiguration("/**", corsConfiguration);//Cette ligne fait le lien entre => une URL :une configuration CORS(Applique corsConfiguration à toutes les routes)
        return source;
    }
}

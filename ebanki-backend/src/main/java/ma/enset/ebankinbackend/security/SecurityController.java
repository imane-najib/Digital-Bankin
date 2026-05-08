package ma.enset.ebankinbackend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/auth") //toutes les routes commencent par /auth
public class SecurityController {

    // (Spring va injecter le bean qu'on créer AuthentificationManager qu'on créer dans SecurityConfiguration)
    @Autowired
    private AuthenticationManager authentificationManager;
    @Autowired //de meme on injecte le enconder qu'on a crée
    private JwtEncoder jwtEncoder;


    @GetMapping("/profile")
    public Authentication authentication(Authentication authentication){
        return authentication;

    }


    @PostMapping("/login")
    public Map<String ,String> login(String username , String password){//les parametre sont par defaut des requestParam
        //faire passer au authentificatermanager qu'on a créer le user avec password et username
        //UsernamePasswordAuthenticationToken(username, password)= c’est juste un conteneur des identifiants envoyés par le client
        Authentication authentification= authentificationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
        Instant instant=Instant.now();//Récupérer le temps actuel
        String scope = authentification.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(" "));
        //authentification.getAuthorities() → récupère les rôles (ex: USER, ADMIN)
        //.map(a -> a.getAuthority()) → transforme chaque rôle en texte
        //.collect(Collectors.joining("")) → les concatène en une string
        System.out.println("SCOPE = " + scope);
        JwtClaimsSet jwtClaimsSet=JwtClaimsSet.builder()//Création du JWT (payload)
                .issuedAt(instant)//date de création du token
                .expiresAt(instant.plus(100, ChronoUnit.MINUTES))//expiration dans 10 minutes
                .subject(username)//identifie l’utilisateur dans le token
                .claim("scope",scope) //tu ajoutes une info custom : ici les rôles de l’utilisateur
                .build();



        //Tu prépares ce que tu veux encoder dans le JWT :
        JwtEncoderParameters jwtEncoderParametersrers=
                JwtEncoderParameters.from(
                        JwsHeader.with(MacAlgorithm.HS512).build(),//Le header contient :le type de signature ici : HS512
                        jwtClaimsSet);//ici le payload ce qu'on aconstruit

        String jwt = jwtEncoder.encode(jwtEncoderParametersrers).getTokenValue();//créer jwt et le signer
        return Map.of("acces-tocken",jwt);//l'envoyer au utilsateur
    }



}
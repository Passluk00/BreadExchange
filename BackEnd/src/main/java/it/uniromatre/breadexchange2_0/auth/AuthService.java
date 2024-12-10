package it.uniromatre.breadexchange2_0.auth;

import it.uniromatre.breadexchange2_0.email.EmailService;
import it.uniromatre.breadexchange2_0.email.EmailTemplateName;
import it.uniromatre.breadexchange2_0.role.Role;
import it.uniromatre.breadexchange2_0.security.JwtService;
import it.uniromatre.breadexchange2_0.token.Token;
import it.uniromatre.breadexchange2_0.token.TokenRepository;
import it.uniromatre.breadexchange2_0.user.User;
import it.uniromatre.breadexchange2_0.user.UserRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;


    @Value("${application.security.mailing.frontend.activation-url}")
    private String activationUrl;



    /*
    *   Registrazione
    */

    public void register(RegistrationRequest request) throws MessagingException {

        var user = User.builder()
                .email(request.getEmail())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .accountLocked(false)
                .enabled(false)
                .roles(Role.CUSTOMER)
                .build();

        userRepository.save(user);
        sendValidationEmail(user);
    }

    public void adminRegisterAndActive(RegistrationRequest request){
        User user = User.builder()
                .email(request.getEmail())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .accountLocked(false)
                .enabled(true)
                .roles(Role.ADMIN)
                .build();

        userRepository.save(user);
    }



    /*
    *       Autenticazione
    */

    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var claims = new HashMap<String , Object>();
        var user = ((User) auth.getPrincipal());
        claims.put("username", user.getUsername());

        // disattivare tutti i token attivi se ci sono al momento dell'autenticazione

        jwtService.revokeAllUserTokens(user);
        var jwtToken = jwtService.generateTokenESalva(claims,user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }



    /*
    *       Attivazione account
    */

    public void activateAccount(String token) throws MessagingException {
        Token savedToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid Token"));

        if(LocalDateTime.now().isAfter(savedToken.getExpiredAt())) {
            sendValidationEmail(savedToken.getUser());
            throw new RuntimeException("Activation token has expired. A new token has been sent");
        }
        var user = userRepository.findById(savedToken.getUser().getId())
                .orElseThrow(() -> new UsernameNotFoundException("User not Found"));
        user.setEnable(true);
        userRepository.save(user);
        savedToken.setValidatedAt(LocalDateTime.now());
        tokenRepository.save(savedToken);
    }



    /*
    *       Invia Codice Autenticazione
    */

    private void sendValidationEmail(User user) throws MessagingException {

        var newToken = generateAndSaveActivationToken(user);

        emailService.sendEmail(
                user.getEmail(),
                user.getUsername(),
                EmailTemplateName.ACTIVATE_ACCOUNT,
                activationUrl,
                    newToken,
                "Account activation"
        );
    }



    /*
    *       Genera codice Autenticazione
    */

    private String generateAndSaveActivationToken(User user) {

        String generatedToken = generateActivationCode(6);

        var token = Token.builder()
                .token(generatedToken)
                .createdAt(LocalDateTime.now())
                .expiredAt(LocalDateTime.now().plusMinutes(15))
                .user(user)
                .build();
        tokenRepository.save(token);
        return generatedToken;
    }

    private String generateActivationCode(int lenght) {

        String character = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();

        for (int i = 0; i < lenght; i++) {
            int randomIndex = secureRandom.nextInt(character.length());
            codeBuilder.append(character.charAt(randomIndex));
        }
        return codeBuilder.toString();
    }



    /*
    *       Revoca Validita Token
    */







}

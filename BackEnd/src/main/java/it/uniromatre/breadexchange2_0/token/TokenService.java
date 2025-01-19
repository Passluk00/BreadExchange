package it.uniromatre.breadexchange2_0.token;

import it.uniromatre.breadexchange2_0.email.EmailService;
import it.uniromatre.breadexchange2_0.email.EmailTemplateName;
import it.uniromatre.breadexchange2_0.user.User;
import it.uniromatre.breadexchange2_0.user.UserRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TokenService {


    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final EmailService emailService;




    // invia Token

    public void sendToken(User user) throws MessagingException{

        var newToken = generateToken(user);


        emailService.sendEmailForVerification(
                user.getEmail(),
                user.getUserName(),
                EmailTemplateName.VERIFY_IDENTITY,             // da cambiare con email personalizzata
                newToken,
                "Verifica Identità"

        );


    }

    // genera e salva Token


    private String generateToken(User user){

        String generatedToken = generateToken(6);

        var token = Token.builder()
                .token(generatedToken)
                .createdAt(LocalDateTime.now())
                .expiredAt(LocalDateTime.now().plusMinutes(15))
                .user(user)
                .build();

        tokenRepository.save(token);
        return generatedToken;
    }

    private String generateToken(int n){

        String character = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();

        for (int i = 0; i < n; i++) {
            int randomIndex = secureRandom.nextInt(character.length());
            codeBuilder.append(character.charAt(randomIndex));
        }
        return codeBuilder.toString();

    }









    // verifica token



}

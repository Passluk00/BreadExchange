package it.uniromatre.breadexchange2_0.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import it.uniromatre.breadexchange2_0.user.User;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;


@Service
@AllArgsConstructor

public class JwtService {

    JwtService(){
        this.secretKey = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";
        this.jwtExpiration = 86400000;
    }

    private static final Logger log = LoggerFactory.getLogger(JwtService.class);
    private String secretKey;
    private long jwtExpiration;


    @Value("${application.security.jwt.refreshExpiration}")          // tempo di vita token riscritto   uso per test
    private long refreshExpiration;

    @Autowired
    private  JwtTokenRepository jwtTokenRepository;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }



    public String generateTokenESalva(
            Map<String, Object> extraClaims,
            User user
    ) {
        String token = buildToken(extraClaims, user, jwtExpiration);
        jwtTokenRepository.save(JwtToken.builder()
                .token(token)
                .revoked(false)
                .expired(false)
                .userId(user.getId())
                .build()
        );
        return token;
    }

    private String buildToken1(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            long expiration
    ) {
        var authorities = userDetails.getAuthorities()
                .stream().
                map(GrantedAuthority::getAuthority)
                .toList();
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .claim("authorities", authorities)
                .signWith(getSignInKey())
                .compact();
    }


    private String buildToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            long expiration
    ) {
        var authorities = userDetails.getAuthorities()
                .stream().
                map(GrantedAuthority::getAuthority)
                .toList();
        return Jwts
                .builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .claim("authorities", authorities)
                .signWith(getSignInKey())
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    public boolean isTokenStillValid(String token){

        var toc = this.jwtTokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Token not found"));

        return !toc.isExpired() && !toc.isRevoked();


        // cerca nel database se il token eseste
        // se esiste controlla se è ancara valido
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    public void revokeAllUserTokens(User user){
        var validUserToken = jwtTokenRepository.findAllValidTokenByUserId(user.getId());
        if(validUserToken.isEmpty())
            return;
        validUserToken.forEach(t -> {
            t.setExpired(true);
            t.setRevoked(true);
        });
        jwtTokenRepository.saveAll(validUserToken);


    }



}

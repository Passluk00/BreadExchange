package it.uniromatre.breadexchange2_0.security;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class JwtToken {

    @Id
    @GeneratedValue
    public Integer id;

    @Column(unique = true, length = 2048)
    public String token;

    public boolean revoked;

    public boolean expired;

    public Integer userId ;





}


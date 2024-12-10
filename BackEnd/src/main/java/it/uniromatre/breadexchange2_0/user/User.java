package it.uniromatre.breadexchange2_0.user;

import it.uniromatre.breadexchange2_0.role.Role;
import it.uniromatre.breadexchange2_0.security.JwtToken;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;

import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="_user")
@EntityListeners(AuditingEntityListener.class)
public class User implements UserDetails , Principal {

    @Id
    @GeneratedValue
    private Integer id;

    private String username;

    private String password;

    @Column(unique=true)
    private String email;

    private String url_picture;

    private String url_BackImg;

    private boolean enabled;

    private boolean accountLocked;

    @OneToMany(mappedBy = "userId")
    private List<JwtToken> tokens;

    // Non Modificare Dati Sensibili


    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdDate;
    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime lastModifiedDate;

    @Enumerated(EnumType.STRING)
    private Role roles;



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.getAuthorities();
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !accountLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    @Override
    public String getName() {
        return this.email;
    }

    public void setEnable(boolean b) {
        this.enabled = b;
    }
}

package it.uniromatre.breadexchange2_0.role;

import lombok.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static it.uniromatre.breadexchange2_0.role.Permission.*;

@RequiredArgsConstructor
public enum Role {

    CUSTOMER(

            Set.of(
                    CUSTOMER_READ,
                    CUSTOMER_UPDATE,
                    CUSTOMER_DELETE,
                    CUSTOMER_CREATE
            )
    ),



    BAKERY_OWNER(

            Set.of(
                    BAKERY_OWNER_READ,
                    BAKERY_OWNER_UPDATE,
                    BAKERY_OWNER_DELETE,
                    BAKERY_OWNER_CREATE

            )
    ),


    ADMIN(

            Set.of(
                    ADMIN_READ,
                    ADMIN_UPDATE,
                    ADMIN_DELETE,
                    ADMIN_CREATE,
                    BAKERY_OWNER_READ,
                    BAKERY_OWNER_UPDATE,
                    BAKERY_OWNER_DELETE,
                    BAKERY_OWNER_CREATE,
                    CUSTOMER_READ,
                    CUSTOMER_UPDATE,
                    CUSTOMER_DELETE,
                    CUSTOMER_CREATE
            )

    );

    @Getter
    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }



}

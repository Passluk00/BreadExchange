package it.uniromatre.breadexchange2_0.role;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    CUSTOMER_READ("customer:read"),
    CUSTOMER_UPDATE("customer:update"),
    CUSTOMER_DELETE("customer:delete"),
    CUSTOMER_CREATE("customer:create"),

    BAKERY_OWNER_READ("bakery_owner:read"),
    BAKERY_OWNER_UPDATE("bakery_owner:update"),
    BAKERY_OWNER_DELETE("bakery_owner:delete"),
    BAKERY_OWNER_CREATE("bakery_owner:create"),

    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete");



    @Getter
    private final String permission;


}

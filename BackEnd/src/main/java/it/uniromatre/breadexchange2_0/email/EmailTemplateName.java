package it.uniromatre.breadexchange2_0.email;

import lombok.Getter;

@Getter
public enum EmailTemplateName {

    ACTIVATE_ACCOUNT("activate_account"),

    VERIFY_IDENTITY("verify_identity"),

    ACCEPTED_REQUEST("accepted_request"),

    REJECT_REQUEST("reject_request");

    private final String name;

    EmailTemplateName(String name) {
        this.name = name;
    }

}

package it.uniromatre.breadexchange2_0.user;


public record UserResponse(

        int id,
        String username,
        String email,
        String url_picture,
        boolean accountLocked,
        boolean enable

) {
}

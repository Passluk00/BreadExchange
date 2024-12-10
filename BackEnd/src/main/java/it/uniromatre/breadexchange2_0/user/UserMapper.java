package it.uniromatre.breadexchange2_0.user;

import org.springframework.stereotype.Service;

@Service
public class UserMapper {

    public UserResponse fromUser(User user){

        if( user == null){
            return null;
        }
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getUrl_picture(),
                user.isAccountLocked(),
                user.isEnabled()
        );
    }

}

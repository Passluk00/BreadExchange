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

    public UserFrontEndResponse forUserFrontEnd(User user){
        return UserFrontEndResponse.builder()
                .id(user.getId())
                .username(user.getUserName())
                .email(user.getEmail())
                .url_picture(user.getUrl_picture())
                .url_back(user.getUrl_BackImg())
                .address(user.getAddress())
                .build();

        // aggiungere altre cose nel caso servono tipo ordini

    }


    public UserFrontEndInfoResponse forUserFrontEndInfo(User user){
        return UserFrontEndInfoResponse.builder()
                .id(user.getId())
                .username(user.getUserName())
                .email(user.getEmail())
                .url_picture(user.getUrl_picture())
                .url_back(user.getUrl_BackImg())
                .address(user.getAddress())
                .build();

        // aggiungere altre cose nel caso servono tipo ordini

    }


}

package it.uniromatre.breadexchange2_0.user;

import it.uniromatre.breadexchange2_0.auth.ChangePasswordRequest;
import it.uniromatre.breadexchange2_0.bakery.registerRequest.BRRRepository;
import it.uniromatre.breadexchange2_0.bakery.registerRequest.BRRService;
import it.uniromatre.breadexchange2_0.bakery.registerRequest.BakeryRegisterRequest;
import it.uniromatre.breadexchange2_0.common.PageResponse;
import it.uniromatre.breadexchange2_0.email.emailVerifyRequest;
import it.uniromatre.breadexchange2_0.file.FileStorageService;
import it.uniromatre.breadexchange2_0.token.TokenRepository;
import it.uniromatre.breadexchange2_0.token.TokenService;
import it.uniromatre.breadexchange2_0.token.tokenVerifyRequest;
import it.uniromatre.breadexchange2_0.token.tokenVerifyResponse;
import it.uniromatre.breadexchange2_0.user.address.*;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final FileStorageService fileStorageService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final AddressMapper addressMapper;
    private final AddressRepository addressRepository;
    private final TokenService tokenService;
    private final TokenRepository tokenRepository;
    private final BRRService brrService;

    public void uploadProfilePicture(MultipartFile file, Authentication connectedUser, int dir) {
        User user = ((User) connectedUser.getPrincipal());
        var picture = fileStorageService.saveFile(file, user.getId());
        if(dir == 1){
            user.setUrl_picture(picture);
        }
        if(dir == 2){
            user.setUrl_BackImg(picture);
        }
        userRepository.save(user);
    }


    public PageResponse<UserResponse> findAllUsers(int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("username").descending());
        Page<User> users = (userRepository.findAll(pageable));

        List<UserResponse> userResponses = users.stream()
                .map(userMapper::fromUser)
                .toList();

        return new PageResponse<>(
                userResponses,
                users.getNumber(),
                users.getSize(),
                users.getTotalElements(),
                users.getTotalPages(),
                users.isFirst(),
                users.isLast()
        );
    }

    public Void banUser(Integer id) {
        User user = userRepository.findUserById(id);

        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + id);
        }

        user.setAccountLocked(!user.isAccountLocked());
        userRepository.save(user);
        return null;
    }



    public void changePassword(
            ChangePasswordRequest request
    ) {

        if (!request.getNewPwd().equals(request.getConfirmNewPwd())) {
            throw new IllegalStateException("Password are not the same");
        }

        var userToken = tokenRepository.findByToken(request.getKey());

        if(userToken == null){
            throw new EntityNotFoundException("User not found");
        }

        User user = userToken.get().getUser();

        user.setPassword(passwordEncoder.encode(request.getConfirmNewPwd()));
        userRepository.save(user);
    }


    public User findById(Integer id) {
        return userRepository.findById(id).orElseThrow( () -> new EntityNotFoundException("User not found with id: " + id));
    }

    //  TODO da finire

    public List<Map<String, String>> getItemsForCarousel() {

        List<Map<String, String>> items = new ArrayList<>();

        for (int i = 0; i <= 5; i++) {

            var user = userRepository.findRandom();

            String name = user.getName();
            String url = user.getUrl_picture();
            String path = "http://localhost:4200/user/" + user.getId();

            items.add(Map.of(
                    "name", name,
                    "url", url,
                    "path", path
            ));
        }
        return items;
    }


    public UserFrontEndResponse getData(Authentication connectedUser) {
        if(connectedUser == null || !connectedUser.isAuthenticated()){
            throw new RuntimeException("Non Sei logato");
        }
        User user = ((User) connectedUser.getPrincipal());
        User test = userRepository.findUserById(user.getId());      // Errore qui Controllare
        if(test == null){
            throw new EntityNotFoundException("User not found with id: " + user.getId());
        }

        return userMapper.forUserFrontEnd(test);
    }

    public UserFrontEndInfoResponse getInfo(Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        User test = userRepository.findUserById(user.getId());
        if(test == null){
            throw new EntityNotFoundException("User not found with id: " + user.getId());
        }
        if(test.getAddress() != null){
            return userMapper.forUserFrontEndInfo(test);
        }
        return null;
    }

    public void addAddress(Authentication connectedUser, NewAddress address) {
        User user = ((User) connectedUser.getPrincipal());
        User test = userRepository.findUserById(user.getId());
        if(test == null){
            throw new EntityNotFoundException("User not found with id: " + user.getId());
        }
        Address add = addressMapper.toAddress(address);
        user.setAddress(add);
        addressRepository.save(add);
        userRepository.save(user);
    }

    public void verificaEmail(emailVerifyRequest request, Authentication connectedUser) throws MessagingException {
        User user = ((User) connectedUser.getPrincipal());
        String userEmail = user.getEmail();
        // controllo che le stringhe siano identiche
        if(!request.email.equals(request.cemail)){
            throw new UsernameNotFoundException("Le Email inserite non sono Identiche"); // creare un nuovo errore personalizzato email non combaciano
        }

        if(!userEmail.equals(request.email)){
            throw new UsernameNotFoundException("Email not match");
            // email dell'utente che ha richiesto il reset della password
            // non coincide con l'email che ha inmesso
        }

        // genera token ed invia email
        tokenService.sendToken(user);
    }



    public tokenVerifyResponse verifyToken(tokenVerifyRequest request, Authentication connectedUser ){

        // verifico se user è valido

        User user = ((User) connectedUser.getPrincipal());
        User currentUser = userRepository.findUserById(user.getId());
        if (currentUser == null) {
            throw new UsernameNotFoundException("User not found with username: " + user.getId());
        }

        // verifico l'esistenza del token

        var tokenToVerify = tokenRepository.findByToken(request.token);
        if(tokenToVerify == null){
            throw new RuntimeException("Token not Found.");
        }


        // verifico che utente Connesso è uguale al propietario del token

        User userToc = tokenToVerify.get().getUser();

        if(!(user.getId().equals(userToc.getId()))){
            throw new RuntimeException("Access denied: user not matching");
        }

        return tokenVerifyResponse.builder()
                .token(tokenToVerify.get().getToken())
                .build();
    }


    public AddressResponse getAddress(Authentication connectedUser) {

        User user = ((User) connectedUser.getPrincipal());
        User currentUser = userRepository.findUserById(user.getId());
        if (currentUser == null) {
            throw new UsernameNotFoundException("User not found with username: " + user.getId());
        }

        if(currentUser.getAddress() == null) {
            throw new RuntimeException("Address not found in user: "+ currentUser.getId());
        }
        log.error("Current user: "+ currentUser);
        return addressMapper.fromAddress(currentUser);
    }


    public void sendRequest(BakeryRegisterRequest request, Authentication connectedUser){

        if(request == null || request.getAddress() == null ){
            throw new RuntimeException("Request is Empty");
        }

        User user = ((User) connectedUser.getPrincipal());
        User currentUser = userRepository.findUserById(user.getId());
        if (currentUser == null) {
            throw new UsernameNotFoundException("User not found with username: " + user.getId());
        }

        var add = request.getAddress();
        addressRepository.save(add);
        request.setUser(currentUser);
        brrService.salvaRequest(request);
    }



}





















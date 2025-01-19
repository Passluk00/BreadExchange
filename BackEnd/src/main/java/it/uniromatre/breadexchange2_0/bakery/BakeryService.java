package it.uniromatre.breadexchange2_0.bakery;

import it.uniromatre.breadexchange2_0.bakery.contact.ContactInfo;
import it.uniromatre.breadexchange2_0.bakery.contact.ContactInfoRepository;
import it.uniromatre.breadexchange2_0.bakery.registerRequest.BRRService;
import it.uniromatre.breadexchange2_0.bakery.registerRequest.BakeryRegisterRequest;
import it.uniromatre.breadexchange2_0.email.EmailService;
import it.uniromatre.breadexchange2_0.email.EmailTemplateName;
import it.uniromatre.breadexchange2_0.role.Role;
import it.uniromatre.breadexchange2_0.user.User;
import it.uniromatre.breadexchange2_0.user.UserRepository;
import it.uniromatre.breadexchange2_0.user.address.Address;
import it.uniromatre.breadexchange2_0.user.address.AddressRepository;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BakeryService {


    private final BakeryRepository bakeryRepository;
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final ContactInfoRepository contactInfoRepository;
    private final BRRService brrService;




                              // sostituibile con id-request
    public void registerBakery(BakeryRegisterRequest request, Authentication connectedUser) throws MessagingException {

        if(request == null){                                                                        // controllo che il file della richiesta sia valido
            throw new RuntimeException("Richiesta invalida dati mancanti");
        }

        User user = ((User) connectedUser.getPrincipal());
        User toCheck = userRepository.findUserById(user.getId());

        if(toCheck==null){                                                                          // controlloche l'utente esista
            throw new EntityNotFoundException("User not Found with id: "+ user.getId());
        }

        if(!toCheck.getRoles().equals(Role.ADMIN)){                                                 // controllo che sia un admin
            throw new SecurityException("User does not have admin permission");
        }


        var bakery = Bakery.builder()

                .name(request.getName())
                .description(request.getDescription())
                .owner(toCheck)
                .registrationDate(LocalDateTime.now())
                .abilitato(true)
                .build();

        var add = Address.builder()

                .name(request.getAddress().getName())
                .telNumber(request.getAddress().getTelNumber())
                .country(request.getAddress().getCountry())
                .state(request.getAddress().getState())
                .provincia(request.getAddress().getProvincia())
                .city(request.getAddress().getCity())
                .postalCode(request.getAddress().getPostalCode())
                .street(request.getAddress().getStreet())
                .number(request.getAddress().getNumber())
                .build();

        var co = ContactInfo.builder()

                .email(request.getEmail_azz())
                .phone(request.getPhone_azz())
                .twitter(request.getTwitter())
                .facebook(request.getFacebook())
                .instagram(request.getInstagram())
                .build();



        bakery.setAddress(add);
        bakery.setContactInfo(co);
        addressRepository.save(add);
        contactInfoRepository.save(co);
        bakeryRepository.save(bakery);
        emailService.sendEmailRejectRequest(
                toCheck.getEmail(),
                toCheck.getUserName(),
                EmailTemplateName.ACCEPTED_REQUEST,
                "Richesta Accettata"
        );

    }

    public void rejectRequest(Authentication connectedUser, BakeryRegisterRequest request) throws MessagingException {


        if(request == null){                                                                        // controllo che il file della richiesta sia valido
            throw new RuntimeException("Richiesta invalida dati mancanti");
        }

        User user = ((User) connectedUser.getPrincipal());
        User toCheck = userRepository.findUserById(user.getId());

        if(toCheck==null){                                                                          // controlloche l'utente esista
            throw new EntityNotFoundException("User not Found with id: "+ user.getId());
        }

        if(!toCheck.getRoles().equals(Role.ADMIN)){                                                 // controllo che sia un admin
            throw new SecurityException("User does not have admin permission");
        }

        request.setUser(toCheck);
        brrService.rimuoviRequest(request);


        emailService.sendEmailRejectRequest(
                toCheck.getEmail(),
                toCheck.getUserName(),
                EmailTemplateName.REJECT_REQUEST,
                "Richesta Rifiutata"
        );


    }

















}

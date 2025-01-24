package it.uniromatre.breadexchange2_0.bakery;

import it.uniromatre.breadexchange2_0.bakery.contact.ContactInfo;
import it.uniromatre.breadexchange2_0.bakery.contact.ContactInfoRepository;
import it.uniromatre.breadexchange2_0.bakery.registerRequest.BRRRepository;
import it.uniromatre.breadexchange2_0.common.PageResponse;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BakeryService {


    private static final Logger log = LoggerFactory.getLogger(BakeryService.class);
    private final BakeryRepository bakeryRepository;
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final ContactInfoRepository contactInfoRepository;
    private final BRRRepository brrRepository;
    private final BakeryMapper bakeryMapper;


    // errore devo cercare se l'utente passato è valido

    public void acceptRequest(Authentication connectedUser, Integer id) throws MessagingException{

        if(id == null){
            throw new RuntimeException("id nullo");
        }

        User user = ((User) connectedUser.getPrincipal());
        User toCheck = userRepository.findUserById(user.getId());

        if(toCheck==null){                                                                          // controlloche l'utente esista
            throw new EntityNotFoundException("User not Found with id: "+ user.getId());
        }

        if(!toCheck.getRoles().equals(Role.ADMIN)){                                                 // controllo che sia un admin
            throw new SecurityException("User does not have admin permission");
        }

        User toSave = userRepository.findUserById(id);
        if(toSave==null){                                                                          // controlloche l'utente esista
            throw new EntityNotFoundException("User not Found with id: "+ id);
        }



        var req = brrRepository.findById(id).orElseThrow(() -> new RuntimeException("Request non esistente con id: "+ id));


        var bakery = Bakery.builder()

                .name(req.getName())
                .description(req.getDescription())
                .owner(toSave)
                .registrationDate(LocalDateTime.now())
                .abilitato(true)
                .build();

        var add = Address.builder()

                .name(req.getAddress().getName())
                .telNumber(req.getAddress().getTelNumber())
                .country(req.getAddress().getCountry())
                .state(req.getAddress().getState())
                .provincia(req.getAddress().getProvincia())
                .city(req.getAddress().getCity())
                .postalCode(req.getAddress().getPostalCode())
                .street(req.getAddress().getStreet())
                .number(req.getAddress().getNumber())
                .build();

        var co = ContactInfo.builder()

                .email(req.getEmail_azz())
                .phone(req.getPhone_azz())
                .twitter(req.getTwitter())
                .facebook(req.getFacebook())
                .instagram(req.getInstagram())
                .build();


        req.setEnable(true);
        brrRepository.save(req);

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

    public void rejectRequestNew(Authentication connectedUser, Integer id) throws MessagingException {

        if(id == null){
            throw new RuntimeException("id nullo");
        }

        User user = ((User) connectedUser.getPrincipal());
        User toCheck = userRepository.findUserById(user.getId());


        if(toCheck==null){                                                                          // controlloche l'utente esista
            throw new EntityNotFoundException("User not Found with id: "+ user.getId());
        }

        if(!toCheck.getRoles().equals(Role.ADMIN)){                                                 // controllo che sia un admin
            throw new SecurityException("User does not have admin permission");
        }

        brrRepository.deleteById(id);
        emailService.sendEmailRejectRequest(
                toCheck.getEmail(),
                toCheck.getUserName(),
                EmailTemplateName.REJECT_REQUEST,
                "Richesta Rifiutata"
        );



    }


    public PageResponse<BakeryResponse> getAllBakery(int page, int size) {

        Pageable pageable = PageRequest.of(page,size, Sort.by("id").descending());
        Page<Bakery> pag = (bakeryRepository.getAll(pageable));
        List<BakeryResponse> bakeryResponses = pag.stream()
                .map(bakeryMapper::fromBakery)
                .toList();

        return new PageResponse<>(
                bakeryResponses,
                pag.getNumber(),
                pag.getSize(),
                pag.getTotalElements(),
                pag.getTotalPages(),
                pag.isFirst(),
                pag.isLast()
        );
    }

    public List<RandomDataBakeryResponse> getRandomData(Integer id) {

        // get Bakery tramite id

        Bakery bac = bakeryRepository.findById(id).orElseThrow(() -> new RuntimeException("Bakery Non Trovato"));


        // get 8 random object      Custom Query
//        List<ItemDesc> obj = itemRepository.findByOwner(bac);

        // map  RanObj  -->  RandomDataBakeryResponse       in una lista



        return null;         // della lista


    }

    public BakeryfrontEndResponse getData(Integer id) {
        Bakery bac = bakeryRepository.findById(id).orElseThrow(() -> new RuntimeException("bakery not found with id: "+id));
        BakeryfrontEndResponse res = bakeryMapper.fromBakeryToFrontEnd(bac);
        return res;
    }
}

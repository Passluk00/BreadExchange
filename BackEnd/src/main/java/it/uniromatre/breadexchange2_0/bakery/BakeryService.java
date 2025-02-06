package it.uniromatre.breadexchange2_0.bakery;

import it.uniromatre.breadexchange2_0.bakery.Week.Week;
import it.uniromatre.breadexchange2_0.bakery.Week.WeekService;
import it.uniromatre.breadexchange2_0.bakery.contact.ContactInfo;
import it.uniromatre.breadexchange2_0.bakery.contact.ContactInfoRepository;
import it.uniromatre.breadexchange2_0.bakery.registerRequest.BRRRepository;
import it.uniromatre.breadexchange2_0.common.PageResponse;
import it.uniromatre.breadexchange2_0.email.EmailService;
import it.uniromatre.breadexchange2_0.email.EmailTemplateName;
import it.uniromatre.breadexchange2_0.file.FileStorageService;
import it.uniromatre.breadexchange2_0.items.category.Category;
import it.uniromatre.breadexchange2_0.items.item.Item;
import it.uniromatre.breadexchange2_0.items.item.ItemMapper;
import it.uniromatre.breadexchange2_0.role.Role;
import it.uniromatre.breadexchange2_0.user.User;
import it.uniromatre.breadexchange2_0.user.UserRepository;
import it.uniromatre.breadexchange2_0.user.address.Address;
import it.uniromatre.breadexchange2_0.user.address.AddressMapper;
import it.uniromatre.breadexchange2_0.user.address.AddressRepository;
import it.uniromatre.breadexchange2_0.user.address.NewAddress;
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
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

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
    private final WeekService weekService;
    private final ItemMapper itemMapper;
    private final FileStorageService fileStorageService;
    private final AddressMapper addressMapper;


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
                .url_picture("./public/testImg/profile.jpeg")
                .url_backImg("./public/testImg/misto_su_tavolo.JPG")
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

        var week = weekService.saveAllClosed();



        req.setEnable(true);
        brrRepository.save(req);

        bakery.setAddress(add);
        bakery.setContactInfo(co);
        bakery.setWeek(week);
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

    //TODO finire get random data

    public List<RandomDataBakeryResponse> getRandomData(Integer id) {

        // get Bakery tramite id

        Bakery bac = bakeryRepository.findById(id).orElseThrow(() -> new RuntimeException("Bakery Non Trovato"));

        List<Category> categorys = bac.getCategories();

        List<Item> items = new ArrayList<>();

        for(Category category : categorys){
            items.addAll(category.getItems());
        }

        Random rand = new Random();

        List<Item> randomItems = new ArrayList<>();

        while(randomItems.size() < 8 && randomItems.size() < items.size()){
            Item randomItem = items.get(rand.nextInt(items.size()));
            if(!randomItems.contains(randomItem)){
                randomItems.add(randomItem);
            }
        }

        return randomItems.stream().map(itemMapper::fromRandom).toList();
    }

    public BakeryfrontEndResponse getData(Integer id) {
        Bakery bac = bakeryRepository.findById(id).orElseThrow(() -> new RuntimeException("bakery not found with id: "+id));
        BakeryfrontEndResponse res = bakeryMapper.fromBakeryToFrontEnd(bac);
        return res;
    }

    public PlanAddBkery getPlaneAdd(int id) {

        var ad = bakeryRepository.findById(id).orElseThrow(() -> new RuntimeException("bakery not found with id: "+id));
        var add = ad.getAddress();

        if(add == null ){
            return null;
        }

        String res = add.getStreet() +' '+ add.getNumber() +' '+ add.getCity() +' '+ add.getProvincia() +' '+ add.getState() +' '+ add.getCountry();
        log.error("dati dal server:"+ res);

        return PlanAddBkery.builder().address(res).build();

    }


    public Week getWeek(Integer idBakery) {
        if(idBakery == null){
            throw new RuntimeException("Id bakery null");
        }

        Bakery bac = bakeryRepository.findById(idBakery).orElseThrow(() -> new RuntimeException("Bakery not found with id: "+ idBakery));

        return bac.getWeek();
    }


    public boolean checkOwner(Integer idBakery, Authentication cu) {
        if (cu == null || !(cu.getPrincipal() instanceof User)) {
            return false;
        }

        User user = (User) cu.getPrincipal();
        User toCheck = userRepository.findUserById(user.getId());

        if (toCheck == null) {
            throw new EntityNotFoundException("User not found with ID: " + user.getId());
        }

        Bakery bakery = bakeryRepository.findById(idBakery)
                .orElseThrow(() -> new EntityNotFoundException("Bakery not found with ID: " + idBakery));

        // Controllo tramite ID per evitare problemi con equals()
        boolean isOwner = bakery.getOwner().getId().equals(toCheck.getId());

        // Controllo se l'utente ha il ruolo ADMIN
        boolean isAdmin = toCheck.getRoles().equals(Role.ADMIN);

        return isOwner || isAdmin;
    }


    public void uploadProfilePicture(MultipartFile file, Authentication connectedUser, Integer idBac) {

        User user = (User)connectedUser.getPrincipal();
        User toCheck = userRepository.findUserById(user.getId());

        if(toCheck == null){
            throw new EntityNotFoundException("User not found with id: "+ user.getId());
        }

        if(idBac == null){
            throw new RuntimeException("BakeryId is empty");
        }

        if(file == null){
            throw new RuntimeException("file is empty");
        }

        Bakery bac = bakeryRepository.getBakeryById(idBac);

        bac.setUrl_picture(fileStorageService.saveFile(file,toCheck.getId()));

        bakeryRepository.save(bac);

    }


    public void addAddress(Authentication connectedUser, NewAddress address, Integer idBac) {

        User user = ((User) connectedUser.getPrincipal());
        User toCheck = userRepository.findUserById(user.getId());

        if(toCheck == null){
            throw new EntityNotFoundException("User not found with id: " + user.getId());
        }

        Bakery bac = bakeryRepository.getBakeryById(idBac);

        if(bac == null){
            throw new EntityNotFoundException("bakery not found with id: "+ idBac);
        }

        if(!toCheck.getId().equals(bac.getOwner().getId())){
            throw new RuntimeException("non sei Il propietario");
        }

        Address add = addressMapper.toAddress(address);
        bac.setAddress(add);
        addressRepository.save(add);
        bakeryRepository.save(bac);
    }


    public void uploadBackPicture(MultipartFile file, Authentication connectedUser, Integer idBac) {

        User user = (User)connectedUser.getPrincipal();
        User toCheck = userRepository.findUserById(user.getId());

        if(toCheck == null){
            throw new EntityNotFoundException("User not found with id: "+ user.getId());
        }

        if(idBac == null){
            throw new RuntimeException("BakeryId is empty");
        }

        if(file == null){
            throw new RuntimeException("file is empty");
        }

        Bakery bac = bakeryRepository.getBakeryById(idBac);

        bac.setUrl_backImg(fileStorageService.saveFile(file,toCheck.getId()));

        bakeryRepository.save(bac);

    }
}









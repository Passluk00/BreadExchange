package it.uniromatre.breadexchange2_0.bakery.registerRequest;

import it.uniromatre.breadexchange2_0.common.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
public class BRRService {

    private final BRRRepository brrRepository;


    public void salvaRequest(BakeryRegisterRequest request){
        brrRepository.save(request);
    }


    // Pageable

    public PageResponse<BakeryRegisterRequest> getAllRequest(int size, int page){

        Pageable pageable = PageRequest.of(page,size, Sort.by("id").descending());
        Page<BakeryRegisterRequest> requests = (brrRepository.findAll(pageable));

        List<BakeryRegisterRequest> requestsResponse = requests.stream().toList();

        return new PageResponse<>(
                requestsResponse,
                requests.getNumber(),
                requests.getSize(),
                requests.getTotalElements(),
                requests.getTotalPages(),
                requests.isFirst(),
                requests.isLast()
        );

    }


    public void rimuoviRequest(BakeryRegisterRequest request){
        brrRepository.delUser(request.getUser());
    }







}

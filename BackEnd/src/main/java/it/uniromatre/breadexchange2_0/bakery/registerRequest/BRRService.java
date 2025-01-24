package it.uniromatre.breadexchange2_0.bakery.registerRequest;

import it.uniromatre.breadexchange2_0.common.PageResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
public class BRRService {

    private static final Logger log = LoggerFactory.getLogger(BRRService.class);
    private final BRRRepository brrRepository;


    public void salvaRequest(BakeryRegisterRequest request){
        brrRepository.save(request);
    }


    // Pageable

    public PageResponse<BakeryRegisterRequest> getAllRequest(int page, int size){

        Pageable pageable = PageRequest.of(page,size, Sort.by("id").descending());
        Page<BakeryRegisterRequest> requests = (brrRepository.findByEnable(pageable));
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


    public PageResponse<BakeryRegisterRequest> search(int page, int size, String name) {

        Pageable pageable = PageRequest.of(page,size, Sort.by("name").descending());
        Page<BakeryRegisterRequest> requests = (brrRepository.findByName(pageable, name));
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
}

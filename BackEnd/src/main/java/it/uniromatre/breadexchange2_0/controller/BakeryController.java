package it.uniromatre.breadexchange2_0.controller;


import it.uniromatre.breadexchange2_0.bakery.BakeryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("bakery")
@AllArgsConstructor
public class BakeryController {

    private final BakeryService bakeryService;



}


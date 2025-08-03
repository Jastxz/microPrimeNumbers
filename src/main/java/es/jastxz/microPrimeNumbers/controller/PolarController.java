package es.jastxz.microPrimeNumbers.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import es.jastxz.microPrimeNumbers.model.CribaRequest;
import es.jastxz.microPrimeNumbers.model.PolarResponse;
import es.jastxz.microPrimeNumbers.service.PolarService;


@RestController
@RequestMapping("/v0/polar")
public class PolarController {

    private final PolarService service;

    public PolarController(PolarService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<PolarResponse> polarCoordenates(@RequestBody CribaRequest request) {
        int number = Math.abs(request.getNumber());
        PolarResponse response = service.polarCoordenates(number);
        return ResponseEntity.ok(response);
    }
}

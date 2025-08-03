package es.jastxz.microPrimeNumbers.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.jastxz.microPrimeNumbers.model.CribaRequest;
import es.jastxz.microPrimeNumbers.model.DiffResponse;
import es.jastxz.microPrimeNumbers.service.DiffService;

@RestController
@RequestMapping("/v0/diff")
public class DiffController {
    
    private final DiffService service;

    public DiffController(DiffService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<DiffResponse> primeDiff(@RequestBody CribaRequest request) {
        int number = Math.abs(request.getNumber());
        DiffResponse response = service.primeDiff(number);
        return ResponseEntity.ok(response);
    }

}
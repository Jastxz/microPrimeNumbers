package es.jastxz.microPrimeNumbers.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import es.jastxz.microPrimeNumbers.model.CribaRequest;
import es.jastxz.microPrimeNumbers.model.ErrorResponse;
import es.jastxz.microPrimeNumbers.model.PolarResponse;
import es.jastxz.microPrimeNumbers.service.PolarService;
import es.jastxz.microPrimeNumbers.util.Util;


@RestController
@RequestMapping("/v0/polar")
public class PolarController {

    private final PolarService service;

    public PolarController(PolarService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> polarCoordenates(@RequestBody CribaRequest request) {
        try {
            // Validación: si el número es 0 es porque debe haber algún problema en la petición
            if (request.getNumber() == 0) {
                ErrorResponse error = new ErrorResponse(
                    400, 
                    "Bad Request", 
                    "Petición mal formulada"
                );
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            }
            
            int number = Math.abs(request.getNumber());
            
            // Validación: número no puede ser mayor a MAX_NUMBER_LIMIT ya que el micro tiene un límite de potencia computacional
            if (number > Util.MAX_NUMBER_LIMIT) {
                ErrorResponse error = new ErrorResponse(
                    503, 
                    "Service Unavailable", 
                    "El número excede el límite máximo permitido (" + Util.MAX_NUMBER_LIMIT + ")"
                );
                return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(error);
            }
            
            // Procesamiento normal
            PolarResponse response = service.polarCoordenates(number);
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            // Error 500 para cualquier otro problema no controlado
            ErrorResponse error = new ErrorResponse(
                500, 
                "Internal Server Error", 
                "Error interno del servidor"
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}

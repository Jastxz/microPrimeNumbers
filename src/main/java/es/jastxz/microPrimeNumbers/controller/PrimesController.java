package es.jastxz.microPrimeNumbers.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.jastxz.microPrimeNumbers.model.ChartResponse;
import es.jastxz.microPrimeNumbers.model.CribaRequest;
import es.jastxz.microPrimeNumbers.model.DiffResponse;
import es.jastxz.microPrimeNumbers.model.ErrorResponse;
import es.jastxz.microPrimeNumbers.model.PolarResponse;
import es.jastxz.microPrimeNumbers.service.PrimesService;
import es.jastxz.microPrimeNumbers.util.Util;

@RestController
@RequestMapping("/v0")
public class PrimesController {
    private final PrimesService service;

    public PrimesController(PrimesService service) {
        this.service = service;
    }

    @PostMapping("/polar")
    public ResponseEntity<?> polarCoordenates(@RequestBody CribaRequest request) {
        try {
            // Validación: si el número es 0 es porque debe haber algún problema en la
            // petición
            if (request.getNumber() == 0) {
                ErrorResponse error = new ErrorResponse(
                        400,
                        "Bad Request",
                        "Petición mal formulada");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            }

            int number = Math.abs(request.getNumber());

            // Validación: número no puede ser mayor a MAX_NUMBER_LIMIT ya que el micro
            // tiene un límite de potencia computacional
            if (number > Util.MAX_NUMBER_LIMIT) {
                ErrorResponse error = new ErrorResponse(
                        503,
                        "Service Unavailable",
                        "El número excede el límite máximo permitido (" + Util.MAX_NUMBER_LIMIT + ")");
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
                    "Error interno del servidor");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @PostMapping("/diff")
    public ResponseEntity<?> primeDiff(@RequestBody CribaRequest request) {
        try {
            // Validación: si el número es 0 es porque debe haber algún problema en la
            // petición
            if (request.getNumber() == 0) {
                ErrorResponse error = new ErrorResponse(
                        400,
                        "Bad Request",
                        "Petición mal formulada");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            }

            int number = Math.abs(request.getNumber());

            // Validación: número no puede ser mayor a MAX_NUMBER_LIMIT ya que el micro
            // tiene un límite de potencia computacional
            if (number > Util.MAX_NUMBER_LIMIT) {
                ErrorResponse error = new ErrorResponse(
                        503,
                        "Service Unavailable",
                        "El número excede el límite máximo permitido (" + Util.MAX_NUMBER_LIMIT + ")");
                return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(error);
            }

            // Procesamiento normal
            DiffResponse response = service.primeDiff(number);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            // Error 500 para cualquier otro problema no controlado
            ErrorResponse error = new ErrorResponse(
                    500,
                    "Internal Server Error",
                    "Error interno del servidor");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @PostMapping("/chart")
    public ResponseEntity<?> primeChart(@RequestBody CribaRequest request) {
        try {
            // Validación: si el número es 0 es porque debe haber algún problema en la
            // petición
            if (request.getNumber() == 0) {
                ErrorResponse error = new ErrorResponse(
                        400,
                        "Bad Request",
                        "Petición mal formulada");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            }

            int number = Math.abs(request.getNumber());

            // Validación: número no puede ser mayor a MAX_NUMBER_LIMIT ya que el micro
            // tiene un límite de potencia computacional
            if (number > Util.MAX_NUMBER_LIMIT) {
                ErrorResponse error = new ErrorResponse(
                        503,
                        "Service Unavailable",
                        "El número excede el límite máximo permitido (" + Util.MAX_NUMBER_LIMIT + ")");
                return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(error);
            }

            // Procesamiento normal
            ChartResponse response = service.chart(number);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            // Error 500 para cualquier otro problema no controlado
            ErrorResponse error = new ErrorResponse(
                    500,
                    "Internal Server Error",
                    "Error interno del servidor");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @PostMapping("/inverse-constant")
    public ResponseEntity<?> analyzeInverseConstant(@RequestBody CribaRequest request) {
        try {
            if (request.getNumber() == 0) {
                ErrorResponse error = new ErrorResponse(400, "Bad Request", "Petición mal formulada");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            }

            int number = Math.abs(request.getNumber());

            // Limit strict for this heavy calculation
            if (number > 2000) {
                ErrorResponse error = new ErrorResponse(503, "Service Unavailable",
                        "El límite para este cálculo es 2000");
                return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(error);
            }

            es.jastxz.microPrimeNumbers.model.InverseConstantResponse response = service.analyzeInverseConstant(number);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse(500, "Internal Server Error",
                    "Error interno del servidor: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}

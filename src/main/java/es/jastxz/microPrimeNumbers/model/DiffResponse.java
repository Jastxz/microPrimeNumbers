package es.jastxz.microPrimeNumbers.model;

import java.util.List;

import lombok.Data;

@Data
public class DiffResponse {
    
    private final List<Integer> primos;
    private final List<Integer> diferencias;
    private final String mensaje;

}

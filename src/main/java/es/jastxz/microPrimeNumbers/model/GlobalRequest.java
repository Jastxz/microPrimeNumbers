package es.jastxz.microPrimeNumbers.model;

import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class GlobalRequest {
    
    private int requestCount; // Contador de peticiones global
    private long lastRequestTimestamp; // Timestamp de la última petición

}
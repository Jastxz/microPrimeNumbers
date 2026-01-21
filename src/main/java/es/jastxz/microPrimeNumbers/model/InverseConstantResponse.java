package es.jastxz.microPrimeNumbers.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InverseConstantResponse {
    private int n;
    private String An; // Copeland-Erdos Constant (truncated)
    private String Bn; // Reciprocal (target)
    private String Cn; // Approximation
    private String difference; // Error (Bn - Cn)
    private double ratioConvergence; // Bn / (n * log n)
}

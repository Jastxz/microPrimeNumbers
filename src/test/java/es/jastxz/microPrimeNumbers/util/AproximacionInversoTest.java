package es.jastxz.microPrimeNumbers.util;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AproximacionInversoTest {

    @Test
    public void testFirst10Primes() {
        // First 10 primes: 2, 3, 5, 7, 11, 13, 17, 19, 23, 29
        // Concatenation: 0.2357111317192329
        int n = 10;
        List<Integer> primes = AproximacionInverso.getPrimes(n);
        assertEquals(10, primes.size());
        assertEquals(29, primes.get(9));

        BigDecimal An = AproximacionInverso.calculateAn(primes);
        assertTrue(An.toString().startsWith("0.2357111317192329"));

        BigDecimal Bn = AproximacionInverso.calculateBn(An);
        // 1 / 0.2357111317192329 approx 4.24248...
        // Let's just check it's not null and positive
        assertNotNull(Bn);
        assertTrue(Bn.doubleValue() > 4.0);
        assertTrue(Bn.doubleValue() < 4.5);
    }
}

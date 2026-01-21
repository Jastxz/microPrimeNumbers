package es.jastxz.microPrimeNumbers.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AproximacionInverso {

    public static void main(String[] args) {
        System.out.println("\nConvergence analysis:");
        System.out.println("-------------------------");
        List<Integer> primes = getPrimes(1000);
        BigDecimal An = calculateAn(primes);
        BigDecimal Bn = calculateBn(An);
        Double BnDouble = Bn.doubleValue();
        An = An.stripTrailingZeros();
        System.out.println("An = " + An);
        System.out.println("Bn = " + Bn);
        System.out.println("BnDouble = " + BnDouble);
        Double Cn = 3 * Math.sqrt(2)
                - Math.sqrt(229) / 100000
                - Math.sqrt(71) / 1000000
                + Math.sqrt(59) / 100000000
                - Math.sqrt(103) / 10000000000.0;
        System.out.println("Cn (Óptimo) = " + Cn);
        System.out.println("Diferencia (Bn - Cn): " + (BnDouble - Cn));
        System.out.println("Resultado (1/Cn): " + 1 / Cn);

    }

    /**
     * Generates the first n prime numbers.
     * Uses cribaEratostenes with an estimated upper bound.
     */
    public static List<Integer> getPrimes(int n) {
        if (n < 1)
            return new ArrayList<>();

        // Estimate upper bound for p_n: n * (ln n + ln ln n)
        // For small n, use a safe static bound or just a larger multiplier
        int limit;
        if (n < 10) {
            limit = 30;
        } else {
            double estimate = n * (Math.log(n) + Math.log(Math.log(n)));
            limit = (int) (estimate * 1.2); // 20% buffer
        }

        List<Integer> allPrimes = Util.cribaEratostenes(limit);

        if (allPrimes.size() < n) {
            // Fallback if estimate was too low (rare with 1.2x)
            return Util.cribaEratostenes(limit * 2).subList(0, n);
        }

        return allPrimes.subList(0, n);
    }

    /**
     * Constructs An = 0.p1p2p3...pn
     */
    public static BigDecimal calculateAn(List<Integer> primes) {
        StringBuilder sb = new StringBuilder("0.");
        for (Integer p : primes) {
            sb.append(p);
        }
        return new BigDecimal(sb.toString(), PrecisionMath.MC);
    }

    /**
     * Calculates Bn = 1 / An
     */
    public static BigDecimal calculateBn(BigDecimal An) {
        return BigDecimal.ONE.divide(An, PrecisionMath.MC);
    }
}

package es.jastxz.microPrimeNumbers.util;

import java.math.MathContext;
import java.math.RoundingMode;

/**
 * Utility class for high-precision arithmetic operations.
 * Wraps a global MathContext to ensure consistent precision across
 * calculations.
 */
public class PrecisionMath {

    // Define a high precision context, e.g., 2000 digits.
    // This should be sufficient for the "many decimals" requirement.
    public static final int PRECISION_DIGITS = 2000;

    public static final MathContext MC = new MathContext(PRECISION_DIGITS, RoundingMode.HALF_UP);

    private PrecisionMath() {
        // Prevent instantiation
    }
}

package hu.jakab.ekkeencoprosampbackend.service.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class SignificantFiguresUtil {

    // Private constructor to prevent instantiation
    private SignificantFiguresUtil() {
        throw new UnsupportedOperationException("Utility class - cannot be instantiated");
    }

    public static BigDecimal roundToSignificantFigures(BigDecimal value, int significantFigures) {
        if (value.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO; // If value is 0, return 0 directly.
        }

        // Determine the scale (order of magnitude)
        int scale = significantFigures - value.precision() + value.scale();

        // Apply rounding with HALF_UP
        BigDecimal roundedValue = value.setScale(scale, RoundingMode.HALF_UP);

        // Enforce minimum value of 0.001
        BigDecimal minValue = new BigDecimal("0.001");
        if (roundedValue.compareTo(minValue) < 0) {
            return minValue;
        }

        return roundedValue;
    }
}

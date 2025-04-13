package hu.jakab.ekkeencoprosampbackend.service.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class SignificantFiguresUtil {

    private SignificantFiguresUtil() {
        throw new UnsupportedOperationException("Utility class - cannot be instantiated");
    }

    public static BigDecimal roundToSignificantFigures(BigDecimal value, int significantFigures) {
        if (value.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }

        int scale = significantFigures - value.precision() + value.scale();

        BigDecimal roundedValue = value.setScale(scale, RoundingMode.HALF_UP);

        BigDecimal minValue = new BigDecimal("0.001");
        if (roundedValue.compareTo(minValue) < 0) {
            return minValue;
        }

        return roundedValue;
    }
}

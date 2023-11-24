package goats.ui.utils;

import java.awt.*;

public class GameWidgetsUtils {

    private final static double MEDIUM_CHARGE_COEFFICIENT = 0.7;
    private final static double LOW_CHARGE_COEFFICIENT = 0.3;
    private final static int EMPTY_CHARGE_VALUE = 0;

    public static Color volumwStepAndPowerTextColor(int charge, int maxCharge) {
        Color stepAndpowerTextColor = Color.BLACK;

        double mediumCharge = MEDIUM_CHARGE_COEFFICIENT * maxCharge;
        double lowCharge = LOW_CHARGE_COEFFICIENT * maxCharge;

        if (charge >= mediumCharge) stepAndpowerTextColor = Color.BLACK;
        if (charge >= lowCharge && charge < mediumCharge) stepAndpowerTextColor = Color.BLUE;
        if (charge >= EMPTY_CHARGE_VALUE && charge < lowCharge) stepAndpowerTextColor = Color.RED;

        return stepAndpowerTextColor;
    }

}

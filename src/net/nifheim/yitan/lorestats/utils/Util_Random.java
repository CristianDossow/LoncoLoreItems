package net.nifheim.yitan.lorestats.utils;

import java.text.DecimalFormat;
import java.util.Locale;
import java.util.Random;

public class Util_Random {

    public int random(int length) {
        return new Random().nextInt(length) + 1;
    }

    public int randomRange(int min, int max) {
        int random = (int) (min + Math.random() * (max - min));

        return random;
    }

    public double randomDoubleRange(double min, double max) {
        double random = min + Math.random() * (max - min);

        return random;
    }

    public String formattedRandomRange(double min, double max) {
        Locale forceLocale = new Locale("en", "UK");
        String decimalPattern = "0.0";

        DecimalFormat decimalFormat = (DecimalFormat) java.text.NumberFormat.getNumberInstance(forceLocale);
        decimalFormat.applyPattern(decimalPattern);

        String format = decimalFormat.format(min + Math.random() * (max - min));

        return format;
    }
}

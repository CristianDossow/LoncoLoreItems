package net.nifheim.yitan.itemlorestats.Util;

import java.text.DecimalFormat;

public class Util_Format {

    public double format(double value) {
        java.util.Locale forceLocale = new java.util.Locale("en", "UK");
        String decimalPattern = "0.0";

        DecimalFormat decimalFormat = (DecimalFormat) java.text.NumberFormat.getNumberInstance(forceLocale);
        decimalFormat.applyPattern(decimalPattern);

        String format = decimalFormat.format(value);

        return Double.parseDouble(format);
    }

    public String formatString(double value) {
        java.util.Locale forceLocale = new java.util.Locale("en", "UK");
        String decimalPattern = "0.0";

        DecimalFormat decimalFormat = (DecimalFormat) java.text.NumberFormat.getNumberInstance(forceLocale);
        decimalFormat.applyPattern(decimalPattern);

        String format = decimalFormat.format(value);

        return format;
    }
}

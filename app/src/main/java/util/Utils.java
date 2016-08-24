package util;

import java.text.DecimalFormat;

/**
 * Created by user on 22/08/2016.
 */

//static method to format numbers with commas//
public class Utils {

    public static String formatNumber(int value){
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        String formatted = formatter.format(value);

        return formatted;
    }
}

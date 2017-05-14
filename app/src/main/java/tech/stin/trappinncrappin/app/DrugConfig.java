package tech.stin.trappinncrappin.app;

/**
 * Created by Austin on 5/11/2017.
 */

public class DrugConfig {
    public static final String WEED = "WEED";
    public static final String LSD = "LSD";
    public static final String MDMA = "MDMA";

    public static String getKey(int pos) {
        switch (pos) {
            case 0:
                return WEED;
            case 1:
                return LSD;
            case 2:
                return MDMA;
            default:
                return "asdf";
        }
    }
}

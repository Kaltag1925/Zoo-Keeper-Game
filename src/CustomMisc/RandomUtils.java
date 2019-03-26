package CustomMisc;

import Engine.Logic.MainData;

public class RandomUtils {
    public static boolean isBetweenDualInclusive(int x, int lower, int upper) {
        return lower <= x && x <= upper;
    }

    public static boolean isBetweenLowerInclusive(int x, int lower, int upper) {
        return lower <= x && x < upper;
    }

    public static boolean isBetweenDualInclusive(double x, double lower, double upper) {
        return lower <= x && x <= upper;
    }

    public static boolean isBetweenLowerInclusive(double x, double lower, double upper) {
        return lower <= x && x < upper;
    }

    public static double runThroughProbability(double factor, double addPerSuccess, double multiplier, double addition) {
        double returnValue = 0.0;
        double probability = 1.0;
        while (isBetweenDualInclusive(MainData.mainData.getRandom().nextDouble(), 0, probability)) {
            probability /= factor;
            returnValue += addPerSuccess;
        }
        returnValue = returnValue * multiplier + addition;
        return returnValue;
    }
}
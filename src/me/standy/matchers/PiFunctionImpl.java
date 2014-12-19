package me.standy.matchers;

/**
 * @author andrew
 *         Created by andrew on 19.12.14.
 */
class PiFunctionImpl {
    /**
     * This method returns a prefix function of a string s
     * @param s not null string
     * @return prefix function of s
     */
    static int[] getPiFunction(String s) {
        int length = s.length();
        int[] result = new int[length];
        result[0] = 0;
        for (int i = 1; i < length; ++i) {
            result[i] = nextPiValue(result, result[i - 1], s, s.charAt(i));
        }
        return result;
    }

    /**
     * This methods returns the next value of prefix function of string s, considering that previous values are
     * stored in array piFunction and the current pi function equals to currentPi
     * @param piFunction the previous values of prefix function
     * @param currentPi the current value of prefix function
     * @param s the string
     * @param nextChar the next character
     * @return the next value of prefix function
     */
    static int nextPiValue(int[] piFunction, int currentPi, String s, char nextChar) {
        while (nextChar != s.charAt(currentPi) && currentPi > 0)
            currentPi = piFunction[currentPi - 1];
        if (nextChar == s.charAt(currentPi))
            currentPi++;
        return currentPi;
    }
}

import java.util.*;
import java.util.regex.*;

public class Unpacker {

    /**
     * This method returns the result of unpacking the string.
     * During the method, depending on which string was passed in the parameters, the following dynamic arrays are generated:
     * If passed string contains inner repeats:
     * 1) single - containing strings that do not need to be repeated;
     * 2) innerInt - contains the required number of repetitions for the inner string;
     * 3) innerStr - contains inner strings;
     * 4) outerInt - contains the required number of repetitions for the outer string;
     * 5) outerStr - contains outer strings;
     * If passed string do not contains inner repeats:
     * 1) single - containing strings that do not need to be repeated;
     * 2) numberOfRepeat - contains the required number of repetitions for the string;
     * 3) repeatedStrings - contains strings which need to be repeated;
     * After filling the arrays, the convertString method is called:
     * 1) for string contains inner repeats
     * @see Unpacker#convertString(List, List, List, List, List)
     * 2) for string which not contains inner repeats:
     * @see Unpacker#convertString(List, List, List)
     *
     * @param s the string to be unpacked
     * @return unpacked string
     */
    public static String unpackString(String s) {

        if (!isValid(s)) {
            return "String " + s + " is not valid. Please enter a valid string.";
        }

        List<String> single = new ArrayList<>();

        if (isNested(s)) {
            Pattern pattern = Pattern.compile("(\\d+)\\[(\\d+)\\[([a-z]*)\\]([a-z]*)\\]([a-z]*)");
            Matcher matcher = pattern.matcher(s);

            List<Integer> innerInt = new ArrayList<>();
            List<String> innerStr = new ArrayList<>();
            List<Integer> outerInt = new ArrayList<>();
            List<String> outerStr = new ArrayList<>();

            while (matcher.find()) {
                innerInt.add(Integer.parseInt(matcher.group(2)));
                innerStr.add(matcher.group(3));
                outerInt.add(Integer.parseInt(matcher.group(1)));
                outerStr.add(matcher.group(4));
                single.add(matcher.group(5));
            }

            return convertString(innerInt, innerStr, outerInt, outerStr, single);
        }

        List<Integer> numberOfRepeat = new ArrayList<>();
        List<String> repeatedStrings = new ArrayList<>();

        Pattern pattern = Pattern.compile("(\\d+)\\[([a-z]+)");
        Matcher matcher = pattern.matcher(s);

        while (matcher.find()) {
            numberOfRepeat.add(Integer.parseInt(matcher.group(1)));
            repeatedStrings.add(matcher.group(2));
        }
        Pattern sPattern = Pattern.compile("\\]([a-z]+)");
        Matcher sMatcher = sPattern.matcher(s);
        while (sMatcher.find()) {
            single.add(sMatcher.group(1));
        }

        return convertString(numberOfRepeat, repeatedStrings, single);

    }

    /**
     * This method checks if the passed string contains nested repetitions
     * @param s checked string
     * @return true - if passed string contains inner repeats
     */
    public static boolean isNested(String s) {
        Pattern pattern = Pattern.compile("[0-9]+\\[[0-9]+\\[[a-z]+");
        Matcher matcher = pattern.matcher(s);

        return matcher.find();
    }

    /**
     * This method checks if the passed string are valid
     * @param s checked string
     * @return true - if passed string are valid.
     */
    public static boolean isValid(String s) {

        if (s == null || s.isEmpty()) {
            return false;
        }
        int openBracketsCount = 0;
        int closedBracketsCount = 0;

        for (char ch : s.toCharArray()) {
            if (ch == '[') {
                openBracketsCount++;
            }
            if (ch == ']') {
                closedBracketsCount++;
            }
        }

        if (openBracketsCount != closedBracketsCount && (openBracketsCount + closedBracketsCount) % 2 != 0) {
            return false;
        }

        Pattern pattern = Pattern.compile("([0-9]+\\[)(\\w+)");
        Matcher matcher = pattern.matcher(s);

        return matcher.find();
    }

    /**
     * Method for repeating strings the required number of times
     * @param numberOfRepeat - number of repetitions
     * @param repeatedStrings - repeatable stringы
     * @param single - strings that do not need to be repeated
     * @return string containing strings repeated the required number of times
     */
    public static String convertString(List<Integer> numberOfRepeat, List<String> repeatedStrings, List<String> single) {

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < numberOfRepeat.size(); i++) {
            sb.append(String.valueOf(repeatedStrings.get(i)).repeat(Math.max(0, numberOfRepeat.get(i))));
        }

        if (single.size() > 0) {
            for (String s : single) {
                sb.append(s);
            }
        }
        return sb.toString();
    }

    /**
     * Method for repeating strings the required number of times
     * @param innerInt - number of repetition inner string
     * @param innerStr - inner string
     * @param outerInt - number of repetition outer string
     * @param outerStr - outer string
     * @param single - strings that do not need to be repeated
     * @return string containing strings repeated the required number of times
     */
    public static String convertString(List<Integer> innerInt, List<String> innerStr,
                                       List<Integer> outerInt, List<String> outerStr, List<String> single) {

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < outerInt.size(); i++) {
            for (int j = 0; j < outerInt.get(i); j++) {
                sb.append(String.valueOf(innerStr.get(i)).repeat(Math.max(0, innerInt.get(i))));
                sb.append(outerStr.get(i));
            }

        }

        if (single.size() > 0) {
            for (String s : single) {
                sb.append(s);
            }
        }
        return sb.toString();
    }
}
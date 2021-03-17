import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Unwrapper {

    public static String getResult(String s) {

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

            return result(innerInt, innerStr, outerInt, outerStr, single);
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

        return result(numberOfRepeat, repeatedStrings, single);

    }

    public static boolean isNested(String s) {
        Pattern pattern = Pattern.compile("[0-9]+\\[[0-9]+\\[[a-z]+");
        Matcher matcher = pattern.matcher(s);

        return matcher.find();
    }

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

    public static String result(List<Integer> numberOfRepeat, List<String> repeatedStrings, List<String> single) {

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

    public static String result(List<Integer> innerInt, List<String> innerStr,
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

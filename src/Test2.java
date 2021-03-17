import java.util.*;
import java.util.regex.*;

public class Test2 {

    public static void main(String[] args) {

        String s2 = "2[3[x]y]z";
        String s3 = "3[asd]4[z]";

        Pattern inner = Pattern.compile("(\\d+)\\[(\\d+)\\[([a-z]*)\\]([a-z]*)\\]([a-z]*)");
        Matcher matcher = inner.matcher(s2);

        List<Integer> innerRep = new ArrayList<>();
        List<String> innerStr = new ArrayList<>();
        List<Integer> outerRep = new ArrayList<>();
        List<String> outerStr = new ArrayList<>();
        List<String> single = new ArrayList<>();

        while (matcher.find()){
            innerRep.add(Integer.parseInt(matcher.group(2)));
            innerStr.add(matcher.group(3));
            outerRep.add(Integer.parseInt(matcher.group(1)));
            outerStr.add(matcher.group(4));
            single.add(matcher.group(5));
        }

        System.out.println(result(innerRep,innerStr,single,outerRep,outerStr));
    }

    public static boolean isNested(String s){
        Pattern pattern = Pattern.compile("[0-9]\\[[0-9]\\[[a-z]+");
        Matcher matcher = pattern.matcher(s);

        return matcher.find();
    }

    public static String result(List<Integer> innerRep, List<String> innerStr,
                                List<String> single, List<Integer> outerRep, List<String> outerStr ){

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < outerRep.size(); i++) {
            for (int j = 0; j < outerRep.get(i); j++) {
                sb.append(String.valueOf(innerStr.get(i)).repeat(Math.max(0, innerRep.get(i))));
                sb.append(outerStr.get(i));
            }

        }


        if(single.size() > 0){
            for (String s : single) {
                sb.append(s);
            }
        }

        return sb.toString();
    }
}

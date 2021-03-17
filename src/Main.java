import java.util.*;
import java.util.regex.*;

public class Main {
    public static void main(String[] args) {

        String s1 = "3[xyz]2[xy]6[asd]zxc";

        List<Integer> repeats = new ArrayList<>();

        Pattern num = Pattern.compile("(\\d+)\\[");
        Matcher m1 = num.matcher(s1);

        while (m1.find()){
            repeats.add(Integer.parseInt(m1.group(1)));
        }

        List<String> rows = new ArrayList<String>();
        Pattern p = Pattern.compile("\\[([a-z]+)\\]");
        Matcher m = p.matcher(s1);
        while (m.find()) {
            rows.add(m.group(1));
        }

        List<String> single = new ArrayList<>();
        Pattern sing = Pattern.compile("\\]([a-z]+)");
        Matcher m2 = sing.matcher(s1);
        while (m2.find()){
            single.add(m2.group(1));
        }



        System.out.println(result(repeats,rows,single));

    }

    public static String result(List<Integer> nums, List<String> rows, List<String> single ){

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < nums.size(); i++) {
            for (int j = 0; j < nums.get(i); j++) {
                sb.append(rows.get(i));
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

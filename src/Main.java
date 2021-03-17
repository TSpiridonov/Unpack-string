public class Main {
    public static void main(String[] args) {
        String s1 = "3[4[x]xyz]x";
        String s2 = "3[xasd]4[xasdsd]x";
        String s3 = "3[]@[xfa]x4yz]x";
        String s4 = "3[[asxd4[sda]]x";

        System.out.println(Unwrapper.getResult(s4));
    }
    }
}

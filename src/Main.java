public class Main {
    public static void main(String[] args) {
        double eps = 1;
        do {
            eps /= 2;
        }
        while(1 + eps > 1);
        System.out.println(eps);
    }
}
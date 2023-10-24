import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.Scanner;

public class Triangle {
    public static void main(String[] args) throws IOException{
        calcTriangle clt = new calcTriangle();
        clt.solveUp();
        clt.print();
    }
}
class calcTriangle {
    int n;
    double[][] a;
    double[] b;
    double[] x;
    double sum;
    Scanner in;
    PrintWriter out;
    calcTriangle() throws IOException{
        in = new Scanner(new BufferedReader(new FileReader("input.txt")));
        in.useLocale(Locale.US);
        n = in.nextInt();
        a = new double[n+1][n+1];
        b = new double[n+1];
        x = new double[n+1];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                a[i][j] = in.nextDouble();
            }
            b[i] = in.nextDouble();
        }
        in.close();
    }
    void solveUp() {
        for(int i = n; i > 0; i--) {
            sum = 0;
            for (int j = n; j > i; j--) {
                sum += a[i][j] * x[j];
            }
            x[i] = (b[i] - sum)/a[i][i];
        }
    }
    void solveDown() {
        for(int i = 1; i < n+1; i++) {
            sum = 0;
            for(int j = 1; j < i; j++) {
                sum += a[i][j] * x[j];
            }
            x[i] = (b[i] - sum)/a[i][i];
        }
    }
    void print() throws IOException{
        out = new PrintWriter("output.txt");
        for(int i = 1; i <= n; i++) {
            out.print("x_" + i + " = ");
            out.printf(Locale.US, ""+x[i]);
            out.println();
        }
        out.flush();
        out.close();
    }
}
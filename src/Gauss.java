import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.Scanner;

public class Gauss {
    public static void main(String[] args) throws IOException {
        Gcalc gg = new Gcalc();
        gg.solve();
        gg.print();
    }
}
class Gcalc {
    int n;
    double[][] a;
    double[] b;
    double[] x;
    Scanner in;
    PrintWriter out;
    Gcalc() throws IOException {
        in = new Scanner(new BufferedReader(new FileReader("input.txt")));
        in.useLocale(Locale.US);
        n = in.nextInt();
        a = new double[n+1][n+1];
        b = new double[n+1];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                a[i][j] = in.nextDouble();
            }
            b[i] = in.nextDouble();
        }
        in.close();
    }

    void toTriangle() { // прямой ход
        for (int m = 1; m < n; m++) {
            for (int i = m+1; i <= n; i++) {
                double k = a[i][m] / a[m][m];
                for (int j = m+1; j <= n; j++) {
                    a[i][j] = a[i][j] - k * a[m][j];
                }
                b[i] = b[i] - k * b[m];
            }
        }
    }

    void calcX() { // обратный ход
        for (int i = n; i > 0; i--) {
            double sum = 0;
            for (int j = n; j > i; j--) {
                sum += a[i][j]*x[j];
            }
            x[i] = (b[i] - sum)/a[i][i];
        }
    }

    void solve() {
        x = new double[n+1];
        toTriangle();
        calcX();
    }

    void print() throws IOException {
        out = new PrintWriter("output.txt");
        for (int i = 1; i <= n; i++) {
            out.printf(Locale.US,"x_" + i  + " = " + x[i]);
            out.println();
        }
        out.flush();
        out.close();
    }
}

/*
Коэффициенты, а также свободные члены известны. Требуется найти неизвестные,
удовлетворяющие заданной системе.
Будем решать данную систему методом Гаусса. Метод Гаусса состоит из прямого и
обратного ходов. Во время прямого хода равносильными преобразованиями мы сводим
систему к системе с верхней треугольной матрицей, а во время обратного хода – решаем
полученную систему. Итак, прямой ход состоит изэтапа, на каждом этапе решения будем
делать некоторые допущения, без которых метод Гаусса не будет работать. На первом этапе
мы исключаем неизвестную из всех уравнений, начиная со второго. Для этого умножим
первое уравнение на число , предполагая, что , и вычтем из уравнения, где . Аналогично на
втором этапе исключаем неизвестную из всех уравнений, начиная с третьего, предполагая,
что новый коэффициент перед переменой во втором уравнении системы отличен от нуля. В
итоге мы получим систему уравнений с верхней треугольной матрицей.
 */
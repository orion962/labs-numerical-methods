import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.Scanner;

public class sqrtMethod {
    public static void main(String[] args) throws IOException {
        useMethod obj = new useMethod();
        obj.solve();
        obj.print();
    }
}
class useMethod {
    int n;
    double[][] a;
    double[][] s;
    double[] b;
    double[] x;
    double[] y;
    double[] d;
    double sum;
    Scanner in;
    PrintWriter out;
    useMethod() throws IOException {
        in = new Scanner(new BufferedReader(new FileReader("input.txt")));
        in.useLocale(Locale.US);
        n = in.nextInt();
        a = new double[n+1][n+1];
        s = new double[n+1][n+1];
        b = new double[n+1];
        d = new double[n+1];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                a[i][j] = in.nextDouble();
            }
            b[i] = in.nextDouble();
        }
        in.close();
    }
    void solve() {
        x = new double[n+1];
        y = new double[n+1];
        for(int i = 1; i <= n; i++) {
            sum = 0;
            for(int k = 1; k <= i-1; k++) {
                sum += Math.pow(s[k][i],2) * d[k];
            }
            d[i] = Math.signum(a[i][i] - sum);
            s[i][i] = Math.sqrt(Math.abs(a[i][i] - sum));
            for(int j = i+1; j <= n; j++) {
                sum = 0;
                for(int k = 1; k <= i-1; k++) {
                    sum += s[k][i] * d[k] * s[k][j];
                }
                s[i][j] = (a[i][j] - sum)/(s[i][i] * d[i]);
            }
        }
        solveDown();
        solveUp();
    }

    void solveDown() {
        for(int i = 1; i <= n; i++) { // trpStimesD[i][j] = s[j][i] * d[j]
            sum = 0;
            for(int j = 1; j < i; j++) {
                sum += s[j][i] * d[j] * y[j];
            }
            y[i] = (b[i] - sum)/(s[i][i] * d[i]);
        }
    }

    void solveUp() {
        for(int i = n; i > 0; i--) {
            sum = 0;
            for (int j = n; j > i; j--) {
                sum += s[i][j] * x[j];
            }
            x[i] = (y[i] - sum)/s[i][i];
        }
    }

    void print() throws IOException{
        out = new PrintWriter("output.txt");
        for (int i = 1; i <= n; i++) {
            out.printf(Locale.US, "x_" + i  + " = " + x[i]);
            out.println();
        }
        out.flush();
        out.close();
    }
}

/*
Коэффициенты, а также свободные члены известны. Известно также, что матрица
(матрица системы) - симметрическая. Требуется найти неизвестные, удовлетворяющие
заданной системе.
Метод квадратного корня основан на разложение матрицы в произведение: ,
где – некоторая верхняя треугольная матрица с , – матрица транспонированная к матрице ,
– диагональная матрица, на главной диагонали которой стоят числа . Получив разложение
матрицы , мы придём к следующей системе: . Обозначив через выражение , мы сведём
решение полученной системы к последовательному решению следующих систем с
треугольными матрицами: , ,
где – нижняя треугольная матрица, – верхняя треугольная матрица.
Элементы матриц будем находить последовательно по следующим формулам: . В общем
случае для : .
Таким образом мы найдём все элементы матриц . Остаётся решить 2 системы уравнений с
верхней и нижней треугольной матрицей соответственно. Как такие системы решаются
описано в Лабораторной работе №0.
 */
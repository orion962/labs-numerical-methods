import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.Scanner;

public class Relax {
    public static void main(String[] args) throws IOException{
        Rcalc obj = new Rcalc(1000);
        obj.solve();
        obj.print();
    }
}
class Rcalc {
    int n;
    int maxItr; // точность вычислений
    int l;
    int m; // число итераций
    double[][] a;
    double[] b;
    double[] x;
    double[] r; // невязки
    double w; // параметр метода релаксации
    double sum;
    double eps;
    Scanner in;
    PrintWriter out;
    Rcalc(int maxItr) throws IOException{
        in = new Scanner(new BufferedReader(new FileReader("input.txt")));
        in.useLocale(Locale.US);
        n = in.nextInt();
        a = new double[n+1][n+1];
        b = new double[n+1];
        x = new double[n+1];
        r = new double[n+1];
        this.maxItr = maxItr;
        l = 19;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                a[i][j] = in.nextDouble();
            }
            b[i] = in.nextDouble();
        }
        eps = in.nextDouble();
        in.close();
    }
    void calcSystem(int k, double w, double eps) {
        double tmp ;
        double norm ;
        m = 0;
        do {
            norm = 0;
            for (int i = 1; i <= n; i++) {
                sum = b[i];
                for (int j = 1; j <= n; j++) {
                    if (j == i) {
                        continue;
                    }
                    sum -= a[i][j] * x[j];
                }
                sum *= w / a[i][i];
                tmp = sum + (1 - w) * x[i];
                norm += (tmp - x[i])*(tmp - x[i]);
                x[i] = tmp;
            }
            m++;
            norm = Math.sqrt(norm);
        }
        while(m <= k && norm > eps);
    }
    void solve() {
        double cur_w;
        double cur_norm;
        double bestNorm = Double.MAX_VALUE;
        for(int s = 1; s <= l; s++) {
            for(int i = 1; i <= n; i++) {
                x[i] = b[i] / a[i][i];
            }
            cur_w = ((double) (2*s)) / ((double) (l+1));
            calcSystem( maxItr/10, cur_w, 0);
            for (int i = 1; i <= n; i++) {
                sum = 0;
                for (int j = 1; j <= n; j++) {
                    sum += a[i][j] * x[j];
                }
                r[i] = sum - b[i];
            }
            sum = 0;
            for (int i = 1; i <= n; i++) {
                sum += r[i]*r[i];
            }
            cur_norm = Math.sqrt(sum);
            if(cur_norm < bestNorm) {
                bestNorm = cur_norm;
                w = cur_w;
            }
        }
        for(int i = 1; i < n+1; i++) {
            x[i] = b[i] / a[i][i];
        }
        calcSystem(maxItr, w, eps);
    }
    void print() throws IOException{
        out = new PrintWriter("output.txt");
        for(int i = 1; i <= n; i++) {
            out.print("x_" + i + " = ");
            out.printf(Locale.US, "" + x[i]);
            out.println();
        }
        out.println("Количество итераций: " + m);
        out.printf(Locale.US, "Заданное эпсилон: " + eps);
        out.println();
        out.printf(Locale.US, "Параметр релаксации: " + w);
        out.flush();
        out.close();
    }
}

/*
Пусть дана система линейных алгебраических уравнений Ax = b. В подробной записи она имеет вид:
{█(a_11 x_1+ a_12 x_2+ ⋯+ a_1n x_n= b_1@a_21 x_1+ a_22 x_2+ ⋯+ a_2n x_n= b_2@⋮@a_n1 x_1+ a_n2 x_2+ ⋯+ a_nn x_n= b_n )┤
Коэффициенты a_ij,i,j= (1,n) ̅ , а также свободные члены b_i,i= (1,n) ̅ известны. Известно также, что матрица A является симметрической и положительно определенной.
Требуется найти неизвестные x_1,x_2,… ,x_n, удовлетворяющие заданной системе.
Будем решать данную систему методом релаксации. Расчётная формула данного метода выглядит следующим образом:
x_i^(k+1)=ω/a_ii  (b_i-∑_(j=1)^(i-1)▒〖a_ij x_j^(k+1) 〗-∑_(j=i+1)^n▒〖a_ij x_j^k 〗)+(1-ω)x_i^k,   i=(1,n) ̅,   k=(0,∞) ̅.
Необходимо подобрать параметр релаксации ω так, чтобы метод сходился наиболее быстро. Это делается с помощью пробных значений. Зададим натуральное l произвольно. Далее возьмём ω_s=2s/(l+1),s=(1,l) ̅, зафиксируем количество итераций k и проведём расчёт для каждого s по формуле метода релаксации. В итоге мы получим l наборов:  x_s^0,…,x_s^k,s=(1,l) ̅.
Далее нужно выяснить при каком s приближение было лучше всего. Для этого нужно подставить все x_s^k в исходную систему Ax=b и вычислить невязки : r^s=Ax_s^k-b. Затем находим невязку наименьшую по норме. Пусть это будет невязка r^(s^* ). Тогда в качестве ω можно взять ω_(s^* ).
 */
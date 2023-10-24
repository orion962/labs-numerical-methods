import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.Scanner;

public class driveMethod {
    public static void main(String[] args) throws IOException{
        mCalc mcalc = new mCalc();
        mcalc.solve();
        mcalc.print();
    }
}
class mCalc {
    int n;
    double[] b; // верхняя диагональ
    double[] c; // главная диагональ
    double[] a; // нижняя диагональ
    double[] f;
    double[] x;
    double[] alpha;
    double[] beta;
    Scanner in;
    PrintWriter out;
    mCalc() throws IOException {
        in = new Scanner(new BufferedReader(new FileReader("input.txt")));
        in.useLocale(Locale.US);
        n = in.nextInt();
        b = new double[n-1];
        c = new double[n];
        a = new double[n];
        f = new double[n];
        x = new double[n];
        alpha = new double[n-1];
        beta = new double[n-1];
        for(int i = 0; i < n-1; i++) {
            b[i] = in.nextDouble();
            b[i] *= -1;
        }
        for(int i = 0; i < n; i++) {
            c[i] = in.nextDouble();
        }
        for(int i = 1; i < n; i++) {
            a[i] = in.nextDouble();
            a[i] *= -1;
        }
        for(int i = 0; i < n; i++) {
            f[i] = in.nextDouble();
        }
        in.close();
    }
    void straightRun() {
        alpha[0] =  b[0] / c[0];
        beta[0] = f[0] / c[0];
        for(int i = 1; i < n-1; i++) {
            alpha[i] =  b[i] / (c[i] - a[i] * alpha[i-1]);
            beta[i] = (f[i] + a[i] * beta[i-1]) / (c[i] - a[i] * alpha[i-1]);
        }
    }
    void reverseRun() {
        x[n-1] = (f[n-1] + a[n-1] * beta[n-2]) / (c[n-1] - a[n-1] * alpha[n-2]);
        for(int i = n-2; i >= 0; i--) {
            x[i] = alpha[i] * x[i+1] + beta[i];
        }
    }
    void solve() {
        straightRun();
        reverseRun();
    }
    void print() throws IOException {
        out = new PrintWriter("output.txt");
        for(int i = 0; i < n; i++) {
            out.printf(Locale.US, "x_" + i  + " = " + x[i]);
            out.println();
        }
        out.flush();
        out.close();
    }
}

/*
Пусть дана система линейных алгебраических уравнений Ax = f, где матрица A является трёхдиагональной, т.е. у этой матрицы все элементы нули, кроме, быть может, элементов вида a_ii,a_(i,i-1),a_(i,i+1). Для удобства обозначим элементы a_(i,i-1) через -a_i, элементы a_ii через c_i, элементы a_(i,i+1) через -b_i. В этом случае система уравнений в подробной записи будет выглядеть следующим образом:
{█(c_1 x_1-b_1 x_2=f_1@-a_i x_(i-1)+c_i x_i-b_i x_(i+1)=f_i,i ∈ (2,n-1) ̅   @-a_n x_(n-1)+c_n x_n=f_n )┤
Требуется найти неизвестные x_1,x_2,… ,x_n, удовлетворяющие заданной системе.
Для решения данной системы будем использовать метод прогонки. Основная идея заключается в том, чтобы установить связь неизвестных компонент x_i:
 x_i= α_i x_(i+1)+β_i,i=(1,n-1) ̅, где α_i,β_i – неизвестные коэффициенты, которые нужно найти. Они находятся по следующим формулам (прямой ход):
α_1=b_1/c_1 ,β_1=f_1/c_1 , α_i=  b_i/(c_i-a_i α_(i-1) ),β_i=(f_i+a_i β_(i-1))/(c_i-a_i α_(i-1) ),   i=(2,n-1) ̅
Далее будем находить сами неизвестные, но в обратном порядке, по следующим формулам (обратный ход):
 */
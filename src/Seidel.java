import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.Scanner;

public class Seidel {
    public static void main(String[] args) throws IOException{
        Scalc obj = new Scalc(2000);
        obj.solve();
        obj.print();
    }
}
class Scalc {
    int n;
    int maxItr; // максимальное количество итераций
    int k ;
    double[][] a;
    double[] b;
    double[] x;
    double sum;
    double eps; // точность вычислений
    double norm;
    Scanner in;
    PrintWriter out;
    Scalc(int maxItr) throws IOException{
        in = new Scanner(new BufferedReader(new FileReader("input.txt")));
        in.useLocale(Locale.US);
        n = in.nextInt();
        a = new double[n+1][n+1];
        b = new double[n+1];
        x = new double[n+1];
        this.maxItr = maxItr;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                a[i][j] = in.nextDouble();
            }
            b[i] = in.nextDouble();
            x[i] = b[i] / a[i][i];
        }
        eps = in.nextDouble();
        in.close();
    }
    void solve() {
        k = 0;
        double tmp;
        do{
            norm = 0;
            for(int i = 1; i <= n; i++) {
                sum = b[i];
                for(int j = 1; j <= n; j++) {
                    if(j == i) {
                        continue;
                    }
                    sum -= a[i][j] * x[j];
                }
                tmp = sum / a[i][i];
                norm += (tmp-x[i])*(tmp-x[i]);
                x[i] = tmp;
            }
            norm = Math.sqrt(norm);
            k++ ;
        }
        while(norm > eps && k < maxItr);
    }
    void print() throws IOException{
        out = new PrintWriter("output.txt");
        for(int i = 1; i <= n; i++) {
            out.print("x_" + i + " = ");
            out.printf(Locale.US, "" + x[i]);
            out.println();
        }
        out.println("Количество итераций: " + k);
        out.printf(Locale.US, "Заданное эпсилон: " + eps);
        out.flush();
        out.close();
    }
}

/*
Пусть дана система линейных алгебраических уравнений Ax = b. В подробной записи она имеет вид:
{█(a_11 x_1+ a_12 x_2+ ⋯+ a_1n x_n= b_1@a_21 x_1+ a_22 x_2+ ⋯+ a_2n x_n= b_2@⋮@a_n1 x_1+ a_n2 x_2+ ⋯+ a_nn x_n= b_n )┤
Коэффициенты a_ij,i,j= (1,n) ̅ , а также свободные члены b_i,i= (1,n) ̅ известны. Известно также, что
∑_((j=1)¦(j≠i))^n▒〖|a_ij |<|a_ii |,i=(1,n) ̅ 〗.
Требуется найти неизвестные x_1,x_2,… ,x_n, удовлетворяющие заданной системе.
Будем решать систему методом Зейделя. Уравнения системы Ax = b имеют вид:
∑_(j=1)^n▒〖a_ij x_j=b_i,   〗 i=(1,n) ̅.
Выделим в каждом уравнении i-е слагаемое:
a_ii x_i+∑_(j=1)^(i-1)▒〖a_ij x_j+∑_(j=i+1)^n▒〖a_ij x_j=b_i,   〗 i=(1,n) ̅.〗
Во всех слагаемых от 1-го до i-го  заменим x_j на компоненты нового вектора x_j^(k+1), а остальные на компоненты известного на k-м шаге итерации компоненты x_j^k. Дело в том, что если идти по i от 1 до n, у нас i-е слагаемое будет неизвестным, а все предыдущие x_j^(k+1)  мы уже нашли, поэтому можем их использовать для нахождения x_i^(k+1). А все x_j^k были найдены на предыдущем шаге итерации. Таким образом, расчётные формулы метода Зейделя выглядят следующим образом:
x_i^(k+1)=1/a_ii  (b_i-∑_(j=1)^(i-1)▒〖a_ij x_j^(k+1) 〗-∑_(j=i+1)^n▒〖a_ij x_j^k 〗),   i=(1,n) ̅,   k=(0,∞) ̅.
 Задать x^0 можно произвольно. Например, следующим образом:
x_i^0=b_i/a_ii ,    i=(1,n) ̅.

 */
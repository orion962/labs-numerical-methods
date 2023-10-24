import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.Scanner;

public class GaussWithChoiceOfMainElem {
    public static void main(String[] args) throws IOException{
        Calc calc = new Calc();
        calc.solve();
        calc.print();
    }
}
class Calc {
    int n;
    double[][] a;
    double[] b;
    double[] x;
    int[] trueOrder;
    int[] help;
    Scanner in;
    PrintWriter out;
    Calc() throws IOException {
        in = new Scanner(new BufferedReader(new FileReader("input.txt")));
        in.useLocale(Locale.US);
        n = in.nextInt();
        a = new double[n+1][n+1];
        b = new double[n+1];
        trueOrder = new int[n+1];
        help = new int[n+1];
        for (int i = 1; i <= n; i++) {
            trueOrder[i] = i;
            help[i] = i;
            for (int j = 1; j <= n; j++) {
                a[i][j] = in.nextDouble();
            }
            b[i] = in.nextDouble();
        }
        in.close();
    }

    void toTriangle() { // прямой ход
        double maxElem;
        double cur;
        int curNum;
        int i_0 = 0;
        int j_0 = 0;
        for (int m = 1; m < n; m++) {
            maxElem = 0;
            for(int i = m; i <= n; i++) { // находим наибольший по модулю элемент
                for(int j = m; j <= n; j++) {
                   if(Math.abs(a[i][j]) > Math.abs(maxElem)) {
                       maxElem = a[i][j];
                       i_0 = i;
                       j_0 = j;
                   }
                }
            }
            for(int j = m; j <= n; j++) { // меняем строки местами
                cur = a[m][j];
                a[m][j] = a[i_0][j];
                a[i_0][j] = cur;

            }
            cur = b[m];
            b[m] = b[i_0];
            b[i_0] = cur;
            for(int i = 1; i <= n; i++) { // меняем столбцы местами
                cur = a[i][m];
                a[i][m] = a[i][j_0];
                a[i][j_0] = cur;
            }
            curNum = trueOrder[m]; // запоминаем правильный порядок
            trueOrder[m] = trueOrder[j_0];
            trueOrder[j_0] = curNum;
            help[trueOrder[m]] = m;
            help[trueOrder[j_0]] = j_0;
            for (int i = m+1; i <= n; i++) {
                double k = a[i][m] / a[m][m]; // a[m][m] != 0
                for (int j = m; j <= n; j++) {
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
                sum = sum + a[i][j]*x[j];
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
            out.printf(Locale.US,"x_" + i  + " = " + x[help[i]]);
            out.println();
        }
        out.flush();
        out.close();
    }
}

/*
Коэффициенты , а также свободные члены известны. Требуется найти неизвестные ,
удовлетворяющие заданной системе.
Будем решать данную систему методом Гаусса с выбором главного элемента. Выбор
главного элемента в матрице (наибольшего по модулю) позволяет добиться устойчивости и
на прямом, и на обратном ходе метода Гаусса. Итак, найдём сначала наибольший по модулю
элемент во всей матрице A. Пусть это будет элемент . Меняем 1-ю и -ю строки местами, не
забыв поменять местами элементы и . Далее меняем местами 1-й и -й столбец и
переобозначим неизвестные, запомнив этот шаг, т.к. это нам понадобится для
восстановления правильного порядка неизвестных в ответе. Дальше исключаем неизвестную
из всех уравнений, начиная со второго. Обозначим через новую матрицу системы.
Повторяем ту же процедуру для остаточной матрицы порядка , которая получается из
матрицы вычеркиванием первой строки и первого столбца. Как и в методе Гаусса, прямой
ход состоит из – го этапа. Далее идёт обратный ход, который абсолютно аналогичен
обратному ходу метода Гаусса. Напоследок осталось только восстановить правильный
порядок неизвестных в ответе.
 */
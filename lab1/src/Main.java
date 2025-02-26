public class Main {

    public static void main(String[] args) {
        homework(5, 1);
    }

    public static void compulsory() {
        String[] languages = {"C", "C++", "C#", "Python", "Go", "Rust", "JavaScript", "PHP", "Swift", "Java"};
        int n = (int) (Math.random() * 1_000_000);
        int r = 0;
        r = r * 3;
        r = r + 0b10101;
        r = r + 0xFF;
        r = r * 6;
        while (r > 9) {
            int suma = 0;
            while (r > 0) {
                suma = suma + r % 10;
                r = r / 10;
            }
            r = suma;

        }
        System.out.println("Willy-nilly, this semester I will learn " + languages[r]);
    }

    public static void homework(int n, int k) {
        String[][] matrix = new String[n + 1][n + 1];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                matrix[i][j] = "0";

        if (2 * k > n) {
            System.out.println("Nu este posibil");
        } else {
            System.out.println("Este posibil");

            for (int i = 0; i < n; i = i + 2)
            {
                matrix[i][i + 1] = matrix[i + 1][i] = "1";
                suma=suma+2;
            }

            int m = n / 2;
            int gradmin, gradmax;
            if (n % 2 == 0) {
                gradmin = 1;
                gradmax = 1;
            } else {
                gradmin = 0;
                gradmax = 1;
            }
            System.out.println("Numarul de muchii este " + m);
            System.out.println("Δ(G)=" + gradmin);
            System.out.println("δ(G)=" + gradmax);

            if (n < 30_000) {
                for (int i = 0; i < n; i++) {
                    String line = new String();
                    for (int j = 0; j < n; j++) {
                        line += matrix[i][j] + " ";
                    }
                    System.out.println(line);
                }
            }
            else {

            }
            }
        }
    }
}


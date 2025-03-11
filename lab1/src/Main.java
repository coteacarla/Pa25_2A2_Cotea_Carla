public class Main {

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int k = Integer.parseInt(args[1]);
        int[] sol = new int[n];
        homework(n, k);
        a = new int[][]{
                {0, 1, 0, 1, 1},
                {1, 0, 1, 1, 0},
                {0, 1, 0, 1, 1},
                {1, 1, 1, 0, 1},
                {1, 0, 1, 1, 0}
        };
        //bonus(a.length, 3);
        //pentru independent set rulez clica si apoi elimin nodurile gasite din multimea de noduri si rulez din nou
    }

    public static void compulsory() {
        String[] languages = {"C", "C++", "C#", "Python", "Go", "Rust", "JavaScript", "PHP", "Swift", "Java"};
        int n = (int) (Math.random() * 1_000_000);
        int r = 0;
        r = r * 3;
        r = r + 0b10101;
        r = r + 0xFF;
        r = r * 6;
        r = r % 9;
        if (r == 0) {
            r = 9;
        }
        System.out.println("Willy-nilly, this semester I will learn " + languages[r]);
    }

    public static void homework(int n, int k) {
        long startTime = System.nanoTime();
        String[][] matrix = new String[n + 1][n + 1];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                matrix[i][j] = "0";

        if (2 * k > n) {
            System.out.println("Nu este posibil");
        } else {
            System.out.println("Este posibil");
            int grade = 0;
            int m = 0;
            for (int i = 0; i < k; i = i + 2) {
                for (int j = 0; j < k; j++) {
                    if (i != j) {
                        matrix[i][j] = matrix[j][i] = "1";
                        grade = grade + 2;
                        m++;
                    }
                }

            }

            System.out.println("Numarul de muchii este " + m);
            System.out.println("Δ(G)=" + "0");
            System.out.println("δ(G)=" + (k-1));

            if (n < 30_000) {
                for (int i = 0; i < n; i++) {
                    String line = new String();
                    for (int j = 0; j < n; j++) {
                        line += matrix[i][j] + " ";
                    }
                    System.out.println(line);
                }
            } else {
                long endTime = System.nanoTime();
                long duration = endTime - startTime;
                System.out.println("Running time (nanoseconds): " + duration);
                System.out.println("Running time (miliseconds): " + (duration / 1000000));
            }

        }
    }

    static int[] candidates, viz, rezultat;
    static int[][] a;
    static int csize = 0, rezultatSize = 0;
    static int gasit = 0;

    public static void bonus(int n, int k) {
        candidates = new int[n];
        viz = new int[n];
        rezultat = new int[k];

        for (int i = 0; i < n; i++) {
            int egrad = 0;
            for (int j = 0; j < n; j++)
                if (a[i][j] == 1)
                    egrad++;

            if (egrad >= k - 1)
                candidates[csize++] = i;
        }

        for (int i = 0; i < csize; i++)
        {
            for (int j = 0; j < n; j++)
                viz[j] = 0;
            rezultatSize = 0;
            bkt(candidates[i], k);
        }
    }

    public static void bkt(int idx, int k) {
        viz[idx] = 1;
        rezultat[rezultatSize++] = candidates[idx];

        if (rezultatSize == k && gasit==0) {
            System.out.println("Exista clica");
            for (int i = 0; i < k; i++)
                System.out.print(rezultat[i] + " ");
            System.out.println();
            gasit=1;
            return;
        }

        for (int i = idx + 1; i < csize; i++) {
            if (viz[i] == 0) {
                boolean ok = true;
                for (int j = 0; j < rezultatSize; j++)
                    if (a[rezultat[j]][candidates[i]] == 0 || a[candidates[i]][rezultat[j]] == 0) {
                        ok = false;
                        break;
                    }
                if (ok) {
                    bkt(i, k);
                    viz[i] = 0;
                    rezultatSize--;
                }
            }
        }
    }
}

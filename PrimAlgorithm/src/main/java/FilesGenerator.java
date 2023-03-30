import java.io.*;

public class FilesGenerator {
    public static int[][] generateFiles(int n) {
        int[][] a = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if ((a[i][j] == 0) & (i != j)) {
                    int k = (int) (Math.random() * 2) == 0 ? (int) (Math.random() * 1000) : 0;
                    a[i][j] = k;
                    a[j][i] = k;
                }
            }
        }
        return a;
    }

    public static void main(String[] args) throws IOException {
        for (int k = 10; k <= 110; k++) {
            int[][] m = generateFiles(k * 7);
            File file = new File("src/main/java/data/file" + (k - 9));
            FileWriter writer = new FileWriter(file);
            for (int i = 0; i < m.length; ++i) {
                for (int j = 0; j < m.length; ++j) {
                    writer.write(m[i][j] + " ");
                }
                writer.write("\n");
            }
            writer.close();
        }
    }
}
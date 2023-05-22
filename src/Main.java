
import javax.swing.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int row = input.nextInt();
        int col = input.nextInt();
        int epsilon = input.nextInt();
        int[][] maze = new int[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                maze[i][j] = input.nextInt();
            }
        }
        int p = input.nextInt();
        int[][] magic = new int[p][3];
        for (int i = 0; i < p; i++) {
            for (int j = 0; j < 3; j++) {
                magic[i][j] = input.nextInt();
            }
        }
        int k = input.nextInt();
        int[] question = new int[k];
        for (int i = 0; i < k; i++) {
            question[i] = input.nextInt();
        }
        SwingUtilities.invokeLater(() -> {
            MazeGameFrame mainFrame = new MazeGameFrame(1250, 1060,epsilon,maze,magic,question);
            mainFrame.setVisible(true);
        });
    }
}

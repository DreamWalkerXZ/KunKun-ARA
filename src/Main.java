import javax.swing.*;
import edu.princeton.cs.algs4.StdIn;

public class Main {
    public static void main(String[] args) {
        int row = StdIn.readInt();
        int col = StdIn.readInt();
        int epsilon = StdIn.readInt();
        int[][] maze = new int[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                maze[i][j] = StdIn.readInt();
            }
        }
        int p = StdIn.readInt();
        int[][] magic = new int[p][3];
        for (int i = 0; i < p; i++) {
            for (int j = 0; j < 3; j++) {
                magic[i][j] = StdIn.readInt();
            }
        }
        int k = StdIn.readInt();
        int[] question = new int[k];
        for (int i = 0; i < k; i++) {
            question[i] = StdIn.readInt();
        }
        // Read arguments from stdin to determine whether to use GUI or CLI
        if (args.length > 0 && args[0].equals("cli")) {
            GameCLI gameCLI = new GameCLI(epsilon,maze,magic,question);
            gameCLI.run();
        }
        if (args.length == 0 || (args.length > 0 && args[0].equals("gui"))) {
            SwingUtilities.invokeLater(() -> {
                MazeGameFrame mainFrame = new MazeGameFrame(1000, 800,epsilon,maze,magic,question);
                mainFrame.setVisible(true);
            });
        }
    }
}

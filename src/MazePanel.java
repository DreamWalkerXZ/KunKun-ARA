import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class MazePanel extends JPanel {
    private int[][] maze;
    private int cellSize;

    public MazePanel(int[][] maze, int cellSize) {
        this.maze = maze;
        this.cellSize = cellSize;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                if (maze[i][j] == 0) {
                    g.setColor(Color.WHITE);
                } else if (maze[i][j] == 1) {
                    g.setColor(Color.BLACK);
                } else if (maze[i][j] == 2) {
                    g.setColor(Color.RED);
                }
                g.fillRect(j * cellSize, i * cellSize, cellSize, cellSize);
            }
        }
    }
}

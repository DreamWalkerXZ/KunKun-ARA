import javax.swing.*;
import java.awt.*;

public class MazePanel extends JPanel {

    private int[][] maze;
    private int cellSize;
    private Color[][] overrideCellColors;
    public boolean[][] invalidMagic;

    public MazePanel(int[][] maze, int cellSize) {
        this.maze = maze;
        this.cellSize = cellSize;
        this.invalidMagic = new boolean[maze.length][maze[0].length];

        overrideCellColors = new Color[maze.length][maze[0].length];
        overrideCellColors[maze.length - 1][maze[0].length - 1] = Color.GREEN;
    }

    public void overrideCellColor(int row, int col, Color color) {
        overrideCellColors[row][col] = color;
        repaint(); // Trigger a call to paintComponent
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                if (overrideCellColors[i][j] == null) {
                    if (maze[i][j] == 0) // Not blocked
                        g.setColor(Color.WHITE);
                    if (maze[i][j] == 1) // Blocked
                        g.setColor(Color.BLACK);
                    if (invalidMagic[i][j])
                        g.setColor(Color.PINK);
                } else {
                    g.setColor(overrideCellColors[i][j]);
                }
                g.fillRect(j * cellSize, i * cellSize, cellSize, cellSize);
            }
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(maze[0].length * cellSize, maze.length * cellSize);
    }
}

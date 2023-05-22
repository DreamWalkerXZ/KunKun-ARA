
import javax.swing.*;
import java.awt.*;


public class Maze extends JComponent {

    private final int Maze_width;
    private final int Maze_height;

    private MazeComponent[][] mazeComponents;
    private final int Cell_size;
    public static JLabel epsilonLabel;
    public static JLabel positionLabel;
    private int epsilon;
    private int[][] maze;
    public static int nikeRow = 0;
    public static int nikeCol = 0;


    public Maze(int width, int height, int epsilon, int[][] maze) {
        Maze_height = maze.length;
        Maze_width = maze[0].length;
        this.epsilon = epsilon;
        mazeComponents = new MazeComponent[Maze_width][Maze_height];
        this.maze = new int[Maze_height][Maze_width];
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                this.maze[i][j] = maze[i][j];
            }
        }
        setLayout(null);
        setSize(width, height);
        Cell_size = width / Maze_width;

        for (int i = 0; i < Maze_height; i++) {
            for (int j = 0; j < Maze_width; j++) {
                if (maze[i][j] == 1)
                    putChessOnBoard(new BlockComponent(new MazePoint(i, j), calculatePoint(i, j), Cell_size));
                else putChessOnBoard(new PathComponent(new MazePoint(i, j), calculatePoint(i, j), Cell_size));
            }
        }
        initNikeOnBoard(0,0);
        epsilonLabel = new JLabel("e = " + epsilon);
        epsilonLabel.setLocation(325, 10);
        epsilonLabel.setSize(300, 60);
        epsilonLabel.setFont(new Font("Rockwell", Font.BOLD, 30));
        positionLabel = new JLabel("Nike is at (0,0)");
        positionLabel.setLocation(675,10);
        positionLabel.setSize(300,60);
        positionLabel.setFont(new Font("Rockwell", Font.BOLD,30));
    }

    public MazeComponent[][] getMazeComponents() {
        return mazeComponents;
    }

    public void putChessOnBoard(MazeComponent mazeComponent) {
        int row = mazeComponent.getChessboardPoint().getX(), col = mazeComponent.getChessboardPoint().getY();

        if (mazeComponents[row][col] != null) {
            remove(mazeComponents[row][col]);
        }
        add(mazeComponents[row][col] = mazeComponent);
    }

    public void swapMazeComponents(MazeComponent chess1, MazeComponent chess2) {

        chess1.swapLocation(chess2);
        int row1 = chess1.getChessboardPoint().getX(), col1 = chess1.getChessboardPoint().getY();
        mazeComponents[row1][col1] = chess1;
        int row2 = chess2.getChessboardPoint().getX(), col2 = chess2.getChessboardPoint().getY();
        mazeComponents[row2][col2] = chess2;
        nikeRow = row1;
        nikeCol = col1;
        if(epsilon > 1) epsilon--;
        epsilonLabel.setText("e = " + epsilon);
        positionLabel.setText("Nike is at (" + nikeRow + "," + nikeCol + ")");
        chess1.repaint();
        chess2.repaint();
    }

    public void blocken(int i, int j){
        putChessOnBoard(new BlockComponent(new MazePoint(i, j), calculatePoint(i, j), Cell_size));
        mazeComponents[i][j].repaint();
    }


    private void initNikeOnBoard(int row, int col) {
        MazeComponent mazeComponent = new NikeComponent(new MazePoint(row, col), calculatePoint(row, col), Cell_size);
        mazeComponent.setVisible(true);
        putChessOnBoard(mazeComponent);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }


    public Point calculatePoint(int row, int col) {
        return new Point(col * Cell_size, row * Cell_size);
    }

    public int getEpsilon() {
        return epsilon;
    }
}

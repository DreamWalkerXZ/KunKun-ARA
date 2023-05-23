import edu.princeton.cs.algs4.Stack;

import javax.swing.*;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MazeGameFrame extends JFrame implements ActionListener {

    private final int WIDTH;
    private final int HEIGTH;
    public final int Maze_width;
    public final int Maze_height;
    private int epsilon;
    Maze Maze;
    private int[][] maze;
    private int[][] magic;
    int magicIndex = 0;
    int questionIndex = 0;
    private int[] question;
    private ARAStar araStar;
    Stack<Node> path;

    public MazeGameFrame(int width, int height, int epsilon, int[][] maze, int[][] magic, int[] question) {
        setTitle("2023 CS203B Project");
        this.WIDTH = width;
        this.HEIGTH = height;
        this.epsilon = epsilon;
        this.Maze_width = WIDTH * 850 / 1000;
        this.Maze_height = HEIGTH * 850 / 1000;
        this.maze = maze;
        this.magic = magic;
        this.question = question;
        araStar = new ARAStar(maze, epsilon);
        path = araStar.iterate(new Node(0, 0, 0, Math.sqrt(Math.pow(maze.length - 1, 2)
                + Math.pow(maze[0].length - 1, 2)), null));
        if (!path.isEmpty())
            path.pop();

        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);

        addMaze();
        addLabel();
        addNextButton();
        add(Maze.epsilonLabel);
        add(Maze.positionLabel);
    }

    private void addMaze() {
        Maze = new Maze(Maze_height, Maze_width, epsilon, maze);
        Maze.setLocation(HEIGTH / 15, HEIGTH / 15);
        add(Maze);
    }

    private void addLabel() {
        JLabel statusLabel = new JLabel("Maze~");
        statusLabel.setLocation(HEIGTH, HEIGTH / 10);
        statusLabel.setSize(200, 60);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(statusLabel);
    }

    private void addNextButton() {
        JButton button = new JButton("Next Step");
        button.setLocation(HEIGTH - 60, 240);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
        button.addActionListener(this);
    }

    public void showPath() {
        JFrame frame = new JFrame("Path at time = " + Maze.getEpsilon());

        int[][] mazeCopy = new int[maze.length][maze[0].length];
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                mazeCopy[i][j] = maze[i][j];
            }
        }

        mazeCopy[Maze.nikeRow][Maze.nikeCol] = 2;

        for (Node node : path) {
            mazeCopy[node.row][node.col] = 2;
        }

        int cellSize = 600 / Math.max(maze.length, maze[0].length);
        MazePanel mazePanel = new MazePanel(mazeCopy, cellSize);
        mazePanel.setPreferredSize(new Dimension(maze[0].length * cellSize, maze.length * cellSize));

        JScrollPane scrollPane = new JScrollPane(mazePanel);
        frame.add(scrollPane);

        frame.pack();
        frame.setVisible(true);
    }

    private void action() {
        if (Maze.getEpsilon() == magic[magicIndex][0]) {
            Maze.blocken(magic[magicIndex][1], magic[magicIndex][2]);
            if (!(Maze.nikeRow == magic[magicIndex][1] && Maze.nikeCol == magic[magicIndex][2])) {
                boolean flag = false;
                for (Node node : path) {
                    if (node.row == magic[magicIndex][1] && node.col == magic[magicIndex][2]) {
                        flag = true;
                        break;
                    }
                }
                maze[magic[magicIndex][1]][magic[magicIndex][2]] = 1;
                if (flag) {
                    araStar = new ARAStar(maze, Maze.getEpsilon());
                    path = araStar.iterate(new Node(Maze.nikeRow, Maze.nikeCol, 0,
                            Math.sqrt(Math.pow(maze.length - 1 - Maze.nikeRow, 2)
                                    + Math.pow(maze[0].length - 1 - Maze.nikeCol, 2)),
                            null));
                    path.pop();
                }
            } else {
                Maze.invalidBlocken(magic[magicIndex][1], magic[magicIndex][2]);
            }
            if (magicIndex < magic.length - 1)
                magicIndex++;
        }
        if (Maze.getEpsilon() == question[questionIndex]) {
            showPath();
            if (questionIndex < question.length - 1)
                questionIndex++;
        }
        if (!path.isEmpty()) {
            Node node = path.pop();
            Maze.swapMazeComponents(Maze.getMazeComponents()[Maze.nikeRow][Maze.nikeCol],
                    Maze.getMazeComponents()[node.row][node.col]);
        }
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        while (Maze.getEpsilon() > question[questionIndex]) {
            action();
        }
        action();
    }

}

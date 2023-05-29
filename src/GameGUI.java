import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import edu.princeton.cs.algs4.Stack;

public class GameGUI {

    private int epsilon;
    private int[][] maze;
    private int[][] magic;
    int magicIndex = 0;
    int questionIndex = 0;
    private int[] question;
    Stack<Node> path;
    private int nikeRow = 0;
    private int nikeCol = 0;
    private int[] goal;
    private Color previousColor = null;
    private JLabel epsilonLabel;
    private MazePanel mazePanel;
    private JFrame frame;

    public GameGUI(int epsilon, int[][] maze, int[][] magic, int[] question) {
        this.epsilon = epsilon;
        this.maze = maze;
        this.magic = magic;
        this.question = question;
        goal = new int[] { maze.length - 1, maze[0].length - 1 };
        frame = new JFrame("GUI");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        int cellSize = 600 / Math.max(maze.length, maze[0].length);
        mazePanel = new MazePanel(maze, cellSize);
        mazePanel.overrideCellColor(nikeRow, nikeCol, Color.ORANGE);
        mazePanel.setPreferredSize(new Dimension(maze[0].length * cellSize, maze.length * cellSize));

        epsilonLabel = new JLabel("Time = " + epsilon);
        epsilonLabel.setHorizontalAlignment(JLabel.CENTER);
        epsilonLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));

        JButton button = new JButton("Execute Action");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonPressed();
            }
        });
        button.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(epsilonLabel, BorderLayout.PAGE_START);
        panel.add(mazePanel, BorderLayout.CENTER);
        panel.add(button, BorderLayout.PAGE_END);

        JScrollPane scrollPane = new JScrollPane(panel);
        frame.add(scrollPane);

        frame.pack();
    }

    public void run() {
        frame.setVisible(true);
    }

    public void showPath() {
        JFrame frame = new JFrame("Path at time = " + epsilon);

        int cellSize = 600 / Math.max(maze.length, maze[0].length);
        MazePanel mazePanelForPath = new MazePanel(maze, cellSize);
        mazePanelForPath.setPreferredSize(new Dimension(maze[0].length * cellSize, maze.length * cellSize));

        mazePanelForPath.overrideCellColor(nikeRow, nikeCol, Color.RED);
        for (Node node : path) {
            mazePanelForPath.overrideCellColor(node.row, node.col, Color.RED);
        }

        JScrollPane scrollPane = new JScrollPane(mazePanelForPath);
        frame.add(scrollPane);

        frame.pack();
        frame.setVisible(true);
    }

    private void buttonPressed() {
        while (question.length > 0 && epsilon > question[questionIndex]) {
            action();
        }
        action();
    }

    private void action() {
        if (magic.length > 0 && epsilon == magic[magicIndex][0]) {
            if (!(nikeRow == magic[magicIndex][1] && nikeCol == magic[magicIndex][2])) {
                maze[magic[magicIndex][1]][magic[magicIndex][2]] = 1;
                mazePanel.overrideCellColor(magic[magicIndex][1], magic[magicIndex][2], Color.MAGENTA);
            } else {
                previousColor = Color.PINK;
            }
            if (magicIndex < magic.length - 1)
                magicIndex++;
        }

        path = new Stack<Node>();
        AStar.findPath(maze, new int[] { nikeRow, nikeCol }, goal, epsilon, path);

        if (question.length > 0 && epsilon == question[questionIndex]) {
            showPath();
            if (questionIndex < question.length - 1)
                questionIndex++;
        }

        if (!path.isEmpty()) {
            // Remove Nike's current position from the path
            path.pop();
            // Move one step further on the path
            if (previousColor != null) {
                mazePanel.overrideCellColor(nikeRow, nikeCol, previousColor);
            } else {
                mazePanel.overrideCellColor(nikeRow, nikeCol, null);
            }
            Node node = path.pop();
            nikeRow = node.row;
            nikeCol = node.col;
            if (mazePanel.getOverrideCellColor(node.row, node.col) == Color.PINK) {
                previousColor = Color.PINK;
            } else {
                previousColor = null;
            }
            mazePanel.overrideCellColor(nikeRow, nikeCol, Color.ORANGE);
            if (epsilon > 1)
                epsilon--;
            epsilonLabel.setText("Epsilon: " + epsilon);
        }
    }
}

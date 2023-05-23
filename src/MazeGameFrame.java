
import edu.princeton.cs.algs4.Stack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MazeGameFrame extends JFrame implements ActionListener{

    private final int WIDTH;
    private final int HEIGTH;
    public final int Maze_width;
    public final int Maze_height;
    private int epsilon;
    private int Cell_Size;
    Maze Maze;
    private int[][] maze;
    private int[][] magic;
    int magicIndex = 0;
    int questionIndex = 0;
    private int[] question;
    private ARAStar araStar;
    Stack<Node> path;

    public MazeGameFrame(int width, int height, int epsilon, int[][] maze, int[][] magic, int[] question) {
        setTitle("2022 CS102A Project Demo"); //设置标题
        this.WIDTH = width;
        this.HEIGTH = height;
        this.epsilon = epsilon;
        this.Maze_width = WIDTH * 850 / 1000;
        this.Maze_height = HEIGTH * 850 / 1000;
        this.maze = new int[maze.length][maze[0].length];
        Cell_Size = width / Maze_width;
        araStar = new ARAStar(maze, epsilon);
        path = araStar.iterate(new Node(0,0,0,maze.length + maze[0].length - 2,null));
        if(!path.isEmpty()) path.pop();
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                this.maze[i][j] = maze[i][j];
            }
        }
        this.magic = new int[magic.length][3];
        for (int i = 0; i < magic.length; i++) {
            for (int j = 0; j < 3; j++) {
                this.magic[i][j] = magic[i][j];
            }
        }
        this.question = new int[question.length];
        for (int i = 0; i < question.length; i++) {
            this.question[i] = question[i];
        }

        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
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

    /**
     * 在游戏面板中添加标签
     */
    private void addLabel() {
        JLabel statusLabel = new JLabel("Maze~");
        statusLabel.setLocation(HEIGTH, HEIGTH / 10);
        statusLabel.setSize(200, 60);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(statusLabel);
    }

    /**
     * 在游戏面板中增加一个按钮，如果按下的话就会显示Hello, world!
     */

    private void addNextButton() {
        JButton button = new JButton("Next Step");
        button.setLocation(HEIGTH - 60,  240);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
        button.addActionListener(this);
    }

    private void Magical(){
        if (Maze.getEpsilon() == magic[magicIndex][0]){
            boolean flag = false;
            for (Node node: path) {
                if (node.row == magic[magicIndex][1] && node.col == magic[magicIndex][2]){
                    flag = true;
                    break;
                }
            }
            Maze.blocken(magic[magicIndex][1],magic[magicIndex][2]);
            maze[magic[magicIndex][1]][magic[magicIndex][2]] = 1;
            if (magicIndex < magic.length-1) magicIndex++;
            if (flag) {
                araStar = new ARAStar(maze, Maze.getEpsilon());
                path = araStar.iterate(new Node(Maze.nikeRow, Maze.nikeCol, 0, maze.length + maze[0].length - Maze.nikeRow - Maze.nikeCol, null));
                path.pop();
            }
        }
    }

    private void Questioning(){
        if (Maze.getEpsilon() == question[questionIndex]){
            JFrame frame = new JFrame("Text Output");
            JTextArea textArea = new JTextArea();
            int[][] mazeCopy = new int[maze.length][maze[0].length];
            for (int i = 0; i < maze.length; i++) {
                for (int j = 0; j < maze[0].length; j++) {
                    mazeCopy[i][j] = maze[i][j];
                }
            }
            for (Node node : path) {
                mazeCopy[node.row][node.col] = 2;
            }
            for (int[] ints : mazeCopy) {
                for (int j = 0; j < maze[0].length; j++) {
                    if (ints[j] == 0) {
                        textArea.append("    ");  // 纯白色方块字符
                    } else if (ints[j] == 1) {
                        textArea.append("\u2588");  // 全黑色方块字符
                    } else if (ints[j] == 2) {
                        textArea.append("\u2591");  // 浅灰色方块字符
                    }
                }
                textArea.append("\n");
            }

            JScrollPane scrollPane = new JScrollPane(textArea);
            frame.add(scrollPane);

            frame.setSize(1250, 1000);
            frame.setVisible(true);
            if (questionIndex < question.length-1) questionIndex++;
        }
    }

    @Override
    public void actionPerformed(ActionEvent event){
        Magical();
        Questioning();
        if (!path.isEmpty()) {
            Node node = path.pop();
            Maze.swapMazeComponents(Maze.getMazeComponents()[Maze.nikeRow][Maze.nikeCol],
                    Maze.getMazeComponents()[node.row][node.col]);
        }
    }

}

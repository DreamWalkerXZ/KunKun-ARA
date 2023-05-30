import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class MazeGenerator {

    // 用户定义部分
    static final int ROWS = 100; // 迷宫的行数
    static final int COLS = 200; // 迷宫的列数
    static final double P = 0.4; // 打通格子的概率，建议大于 0.3
    static int MAGIC_LENGTH = 30; // 魔法的个数
    static int QUESTION_LENGTH = 60; // 问题的个数
    static double epsilon = 100;

    public static void main(String[] args) {
        int[][] maze = generateMaze(ROWS, COLS, P); // 生成一个 ROWS * COLS 的迷宫，打通格子的概率为 P
        int pathLength = isPath(maze);
        while (pathLength == 0) {
            maze = generateMaze(ROWS, COLS, P);
            pathLength = isPath(maze);
        }
        int[][] magic = placeSomeMagic(maze, MAGIC_LENGTH); // 生成一个 3 个魔法的迷宫
        // 确保以 epsilon 搜索到的路径长度小于 epsilon，即有足够的时间走完迷宫
        while (pathLength >= epsilon) {
            epsilon = pathLength + 1;
            pathLength = isPath(maze);
        }
        StdOut.println(ROWS + " " + COLS + " " + (int) (epsilon));
        for (int[] row : maze) {
            for (int cell : row) {
                StdOut.print(cell + " ");
            }
            StdOut.println();
        }
        StdOut.println(MAGIC_LENGTH);
        // 生成 MAGIC_LENGTH 个不重复的小于 pathLength 的数
        if (MAGIC_LENGTH > pathLength) {
            MAGIC_LENGTH = pathLength;
        }
        int[] magicSequence = new int[MAGIC_LENGTH];
        Random random = new Random();
        for (int i = 0; i < MAGIC_LENGTH; i++) {
            int magicTime = random.nextInt(pathLength);
            while (isInArray(magicSequence, magicTime)) {
                magicTime = random.nextInt(pathLength);
            }
            magicSequence[i] = magicTime;
        }
        // 降序排序魔法时机
        magicSequence = Arrays.stream(
                magicSequence).boxed()
                .sorted(Collections.reverseOrder())
                .mapToInt(Integer::intValue)
                .toArray();
        for (int i = 0; i < magicSequence.length; i++) {
            magic[i][0] = magicSequence[i];
        }
        for (int[] row : magic) {
            for (int cell : row) {
                StdOut.print(cell + " ");
            }
            StdOut.println();
        }
        StdOut.println(QUESTION_LENGTH);
        // 生成 QUESTION_LENGTH 个不重复的小于 pathLength 的数
        if (QUESTION_LENGTH > pathLength) {
            QUESTION_LENGTH = pathLength;
        }
        int[] questions = new int[QUESTION_LENGTH];
        for (int i = 0; i < QUESTION_LENGTH; i++) {
            int question = random.nextInt(pathLength);
            while (isInArray(questions, question)) {
                question = random.nextInt(pathLength);
            }
            questions[i] = question;
        }
        // 降序排序问题
        questions = Arrays.stream(
                questions).boxed()
                .sorted(Collections.reverseOrder())
                .mapToInt(Integer::intValue)
                .toArray();
        for (int question : questions) {
            StdOut.println(question);
        }
    }

    private static boolean isInArray(int[] array, int key) {
        for (final int i : array) {
            if (i == key) {
                return true;
            }
        }
        return false;
    }

    public static int[][] generateMaze(int rows, int cols, double p) {
        Random random = new Random();
        int[][] maze = new int[rows][cols];

        // 打通入口和出口
        maze[0][0] = 0;
        maze[rows - 1][cols - 1] = 0;

        // 按照一定的概率打通其他的格子
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                if (random.nextDouble() < p) {
                    maze[j][i] = 0;
                } else {
                    maze[j][i] = 1;
                }
            }
        }

        return maze;
    }

    public static int isPath(int[][] maze) {
        int[] start = { 0, 0 };
        int[] goal = { maze.length - 1, maze[0].length - 1 };
        Stack<Node> path = new Stack<>();
        if (AStar.findPath(maze, start, goal, epsilon, path)) {
            return path.size();
        } else {
            return 0;
        }
    }

    public static int[][] placeSomeMagic(int[][] maze, int n) {
        Random random = new Random();
        int[][] magic = new int[n][3];
        for (int i = 0; i < n; i++) {
            int row = random.nextInt(maze.length);
            int col = random.nextInt(maze[0].length);
            if (maze[row][col] == 1) {
                maze[row][col] = 0;
                magic[i][1] = row;
                magic[i][2] = col;
            } else {
                i--;
            }
        }
        return magic;
    }
}

import java.util.Arrays;
import java.util.Random;

public class MazeGenerator {

    public static void main(String[] args) {
        int[][] maze = generateMazeWithPath(10, 10, 0.8); // 生成一个 10 行 10 列的迷宫, 0.8 是打通格子的概率
        printMaze(maze); // 打印迷宫数组
        System.out.println("isPath: " + isPath(maze)); // 判断迷宫中是否有路径
    }

    public static int[][] generateMaze(int rows, int cols, double p) {
        Random random = new Random();
        int[][] maze = new int[rows][cols];

        // 初始化迷宫，将所有格子设为墙壁
        for (int i = 0; i < rows; i++) {
            Arrays.fill(maze[i], 1);
        }

        // 打通入口和出口
        maze[0][0] = 0;
        maze[rows - 1][cols - 1] = 0;

        // 按照一定的概率打通其他的格子
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                if (random.nextDouble() < p) {
                    maze[j][i] = 0;
                }
            }
        }

        return maze;
    }

    public static void printMaze(int[][] maze) {
        int rows = maze.length;
        int cols = maze[0].length;
        System.out.println("{");
        for (int i = 0; i < rows; i++) {
            System.out.print("    {");
            for (int j = 0; j < cols; j++) {
                System.out.print(maze[i][j]);
                if (j != cols - 1) {
                    System.out.print(", ");
                }
            }
            System.out.print("}");
            if (i != rows - 1) {
                System.out.println(",");
            } else {
                System.out.println();
            }
        }
        System.out.println("}");
    }

    private static void dfs(int[][] maze, boolean[][] visited, int row, int col) {
        int rows = maze.length;
        int cols = maze[0].length;
        if (row < 0 || row >= rows || col < 0 || col >= cols || maze[row][col] == 1 || visited[row][col]) {
            // 超出边界，遇到墙壁或已经访问过的格子，直接返回
            return;
        }
        visited[row][col] = true; // 标记该格子已经访问过
        dfs(maze, visited, row - 1, col); // 上
        dfs(maze, visited, row + 1, col); // 下
        dfs(maze, visited, row, col - 1); // 左
        dfs(maze, visited, row, col + 1); // 右
    }

    public static boolean isPath(int[][] maze) {
        int rows = maze.length;
        int cols = maze[0].length;
        boolean[][] visited = new boolean[rows][cols];
        dfs(maze, visited, 0, 0);
        if (visited[rows - 1][cols - 1]) {
            return true;
        } else {
            return false;
        }
    }

    public static int[][] generateMazeWithPath(int rows, int cols, double p) {
        int[][] maze = generateMaze(rows, cols, p);
        while (!isPath(maze)) {
            maze = generateMaze(rows, cols, p);
        }
        return maze;
    }

}

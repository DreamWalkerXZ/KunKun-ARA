import edu.princeton.cs.algs4.Stack;

public class App {
    public static void main(String[] args) {
        int[][] maze = {
                { 0, 0, 1, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 1, 0 },
                { 0, 0, 0, 0, 0, 1, 1, 0, 0, 0 },
                { 0, 0, 0, 0, 1, 1, 0, 1, 0, 0 },
                { 1, 0, 0, 0, 1, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 1, 1, 0, 0, 0, 0 },
                { 1, 1, 1, 1, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 1, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }
        };
        int[] start = { 0, 0 };
        int[] goal = { maze.length - 1, maze[0].length - 1 };
        double epsilon = 5.0;
        double epsilonDecreaseFactor = 0.9;
        double minEpsilon = 1.0;
        Stack<Node> path = new Stack<>();

        while (epsilon >= minEpsilon) {
            epsilon = ARAStar.findPath(maze, start, goal, epsilon, path);
            int[][] mazeCopy = new int[maze.length][maze[0].length];
            for (int i = 0; i < maze.length; i++) {
                for (int j = 0; j < maze[0].length; j++) {
                    mazeCopy[i][j] = maze[i][j];
                }
            }

            System.out.println("Path with epsilon = " + epsilon + ", length = " + path.size() + ":");
            for (Node node : path) {
                // System.out.println("(" + node.row + ", " + node.col + ")");
                mazeCopy[node.row][node.col] = 2;
            }
            
            // Print the maze with the path
            for (int i = 0; i < mazeCopy.length; i++) {
                for (int j = 0; j < mazeCopy[0].length; j++) {
                    if (mazeCopy[i][j] == 0) {
                        System.out.print("  ");
                    } else if (mazeCopy[i][j] == 1) {
                        System.out.print("██");
                    } else if (mazeCopy[i][j] == 2) {
                        System.out.print("░░");
                    }
                }
                System.out.println();
            }
            System.out.println();

            epsilon *= epsilonDecreaseFactor;
        }
    }
}

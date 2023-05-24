import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.Stopwatch;

public class TestARAStar {
    public static void main(String[] args) {
        // Read maze from stdin
        int n = StdIn.readInt();
        int m = StdIn.readInt();
        int[][] maze = new int[n][m];
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                maze[i][j] = StdIn.readInt();
            }
        }
        // Read epsilon from stdin
        int epsilon = StdIn.readInt();
        
        double totalTime = 0;
        ARAStar araStar = new ARAStar(maze, epsilon);
        for (int i = 0; i < 1000; i++) {
            Stopwatch stopwatch = new Stopwatch();
            Stack<Node> path = araStar.iterate(new Node(0, 0, 0, Math.sqrt(Math.pow(maze.length - 1, 2)
                    + Math.pow(maze[0].length - 1, 2)), null));
            totalTime += stopwatch.elapsedTime();
        }
        System.out.println(totalTime / 1000);
    }    
}

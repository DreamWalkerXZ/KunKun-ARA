import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

public class ARAStar {
    private static final int[][] DIRECTIONS = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };

    public static double findPath(int[][] maze, int[] start, int[] goal, double epsilon, Stack<Node> path) {
        int numRows = maze.length;
        int numCols = maze[0].length;
        
        double sh = heuristic(start, goal);
        double sf = epsilon * sh;
        Node startNode = new Node(start[0], start[1], 0, sh, sf, null);
        MinPQ<Node> openSet = new MinPQ<>();
        openSet.insert(startNode);

        while (!openSet.isEmpty()) {
            Node current = openSet.delMin();

            if (current.row == goal[0] && current.col == goal[1]) {
                while (!path.isEmpty()) {
                    path.pop();
                }
                while (current != null) {
                    path.push(current);
                    current = current.parent;
                }
                return epsilon; // Return the current epsilon value
            }

            for (int[] direction : DIRECTIONS) {
                int newRow = current.row + direction[0];
                int newCol = current.col + direction[1];

                if (isValid(newRow, newCol, numRows, numCols) && maze[newRow][newCol] == 0) {
                    double tentativeG = current.g + 1;
                    double h = heuristic(new int[] { newRow, newCol }, goal);
                    double f = tentativeG + epsilon * h;

                    Node neighbor = new Node(newRow, newCol, tentativeG, h, f, current);
                    openSet.insert(neighbor);
                }
            }
        }

        return Double.POSITIVE_INFINITY; // No path found
    }

    private static double heuristic(int[] a, int[] b) {
        return Math.abs(a[0] - b[0]) + Math.abs(a[1] - b[1]);
    }

    private static boolean isValid(int row, int col, int numRows, int numCols) {
        return row >= 0 && row < numRows && col >= 0 && col < numCols;
    }
}

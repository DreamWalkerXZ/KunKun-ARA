import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

public class AStar {

    private static final int[][] DIRECTIONS = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 }, { -1, -1 }, { -1, 1 },
            { 1, -1 }, { 1, 1 } };

    public static boolean findPath(int[][] maze, int[] start, int[] goal, double epsilon, Stack<Node> path) {
        Node[][] visited = new Node[maze.length][maze[0].length];

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
                return true; // Path found
            }

            for (int[] direction : DIRECTIONS) {
                int newRow = current.row + direction[0];
                int newCol = current.col + direction[1];

                boolean isInBounds = newRow >= 0 && newRow < maze.length && newCol >= 0 && newCol < maze[0].length;
                if (!isInBounds || maze[newRow][newCol] == 1) {
                    continue;
                }

                Node neighbor = visited[newRow][newCol];
                if (neighbor == null) {
                    neighbor = new Node(newRow, newCol, 
                            Double.POSITIVE_INFINITY, heuristic(new int[] { newRow, newCol }, goal), 
                            Double.POSITIVE_INFINITY, current);
                    visited[newRow][newCol] = neighbor;
                }
                if (neighbor.g > current.g + 1) {
                    neighbor.g = current.g + 1;
                    neighbor.f = neighbor.g + epsilon * neighbor.h;
                    openSet.insert(neighbor);
                }
            }
        }

        return false; // No path found
    }

    private static double heuristic(int[] a, int[] b) {
        return Math.sqrt(Math.pow(a[0] - b[0], 2) + Math.pow(a[1] - b[1], 2));
    }

}
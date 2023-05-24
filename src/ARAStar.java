import edu.princeton.cs.algs4.Stack;

import java.util.ArrayList;

public class ARAStar {
    private int[][] maze;
    private Node goal;
    private static final int[][] DIRECTIONS = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 }, { -1, -1 }, { -1, 1 },
            { 1, -1 }, { 1, 1 } };
    private MinPQ<Node> OPEN = new MinPQ<>();
    private ArrayList<Node> CLOSED = new ArrayList<>();
    private MinPQ<Node> INCONS = new MinPQ<>();
    private int height;
    private int width;
    private double epsilon;
    private double epsilonPrime;
    private final double minEpsilon = 1.0;
    public Node[][] accessed;
    private Stack<Node> path;

    public void improvePath(int[][] maze, Node goal, Stack<Node> path) {
        Node s;
        while ((!OPEN.isEmpty()) && ((s = findMinF(OPEN, fValue(goal))) != null)) {
            CLOSED.add(s);

            if (s.row == height - 1 && s.col == width - 1) {
                goal.g = s.g;
                while (!path.isEmpty()) {
                    path.pop();
                }
                while (s != null) {
                    path.push(s);
                    s = s.parent;
                }
                return; // Return the current epsilon value
            }

            for (int[] direction : DIRECTIONS) {
                int newRow = s.row + direction[0];
                int newCol = s.col + direction[1];
                if (newRow >= 0 && newRow < height && newCol >= 0 && newCol < width && maze[newRow][newCol] == 0) {
                    if (accessed[newRow][newCol] == null) {
                        accessed[newRow][newCol] = new Node(newRow, newCol, Double.POSITIVE_INFINITY,
                                heuristic(new int[] { newRow, newCol }, new int[] { height - 1, width - 1 }), s);
                    }
                    Node t = accessed[newRow][newCol];
                    if (t.parent != null && t.parent.g > s.g)
                        t.parent = s;
                    if (t.g > s.g + heuristic(new int[] { s.row, s.col }, new int[] { t.row, t.col })) {
                        t.g = s.g + heuristic(new int[] { s.row, s.col }, new int[] { t.row, t.col });
                        if (!CLOSED.contains(t) && OPEN.index(t) == 0) {
                            OPEN.insert(t);
                        } else
                            INCONS.insert(t);
                    }
                }
            }
        }
    }

    public static double heuristic(int[] a, int[] b) {
        return Math.sqrt(Math.pow(a[0] - b[0], 2) + Math.pow(a[1] - b[1], 2));
    }

    private double fValue(Node s) {
        return s.g + epsilonPrime * s.h;
    }

    private Node findMinF(MinPQ<Node> open, double fGoal) {
        if (!open.isEmpty()) {
            Node node = open.min();
            for (Node node1 : open) {
                if (fValue(node1) < fValue(node)) {
                    node = node1;
                }
            }
            double min = fValue(node);
            Node minNode = new Node(node);
            if (fGoal > min) {
                int index = open.index(node);
                node.g = Double.NEGATIVE_INFINITY;
                node.h = Double.NEGATIVE_INFINITY;
                open.swim(index);
                open.delMin();
                node.g = minNode.g;
                node.h = minNode.h;
                return minNode;
            } else
                return null;
        } else
            return null;
    }

    public double findMinSum() {
        if (!OPEN.isEmpty() && !INCONS.isEmpty()) {
            return Math.min(OPEN.min().g + OPEN.min().h, INCONS.min().g + INCONS.min().h);
        } else if (!OPEN.isEmpty()) {
            return OPEN.min().g + OPEN.min().h;
        } else if (!INCONS.isEmpty()) {
            return INCONS.min().g + INCONS.min().h;
        } else
            return 0.01;
    }

    private void firstIterate(Node start) {
        OPEN.insert(start);
        improvePath(maze, goal, path);
        epsilonPrime = Math.min(epsilon, goal.g / findMinSum());
    }

    private void otherIterate() {
        epsilon -= 1;
        while (!INCONS.isEmpty())
            OPEN.insert(INCONS.delMin());
        CLOSED.clear();
        improvePath(maze, goal, path);
        epsilonPrime = Math.min(epsilon, goal.g / findMinSum());
    }

    public Stack<Node> iterate(Node start) {
        firstIterate(start);
        while (epsilonPrime > minEpsilon) {
            otherIterate();
        }
        return path;
    }

    public ARAStar(int[][] maze, double epsilon) {
        this.maze = maze;
        this.height = maze.length;
        this.width = maze[0].length;
        this.accessed = new Node[height][width];
        this.epsilon = epsilon;
        this.epsilonPrime = epsilon;
        this.goal = new Node(height - 1, width - 1, Double.POSITIVE_INFINITY, 0, null);
        this.path = new Stack<>();
    }
}

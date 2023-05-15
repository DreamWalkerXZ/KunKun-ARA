import edu.princeton.cs.algs4.Stack;

import java.util.ArrayList;

public class ARAStar {
    private static final int[][] DIRECTIONS = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };
    static MinPQ<Node> OPEN = new MinPQ<>();
    static ArrayList<Node> CLOSED = new ArrayList<>();
    static MinPQ<Node> INCONS = new MinPQ<>();
    static int height;
    static int width;
    static double epsilon;
    static double epsilonPrime;
    static double lastEpsilonPrime;
    static Node[][] Accessed;

    public static void improvePath(int[][] maze, Node goal, Stack<Node> path){
        Node s;
        while((!OPEN.isEmpty()) && ((s = findMinF(OPEN,fValue(goal))) != null)) {
            CLOSED.add(s);

            if (s.row == height-1 && s.col == width-1) {
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
                if (newRow >= 0 && newRow < height && newCol >= 0 && newCol < width && maze[newRow][newCol] == 0){
                    if (Accessed[newRow][newCol] == null){
                        Accessed[newRow][newCol] = new Node(newRow,newCol,Double.POSITIVE_INFINITY,
                                heuristic(new int[]{newRow,newCol},new int[]{height-1,width-1}), s);
                    }
                    Node t = Accessed[newRow][newCol];
                    if (t.parent != null && t.parent.g > s.g) t.parent = s;
                    if (t.g > s.g + heuristic(new int[]{s.row,s.col},new int[]{t.row,t.col})){
                        t.g = s.g + heuristic(new int[]{s.row,s.col},new int[]{t.row,t.col});
                        if (!CLOSED.contains(t) && OPEN.index(t) == 0){
                            OPEN.insert(t);
                        }
                        else INCONS.insert(t);
                    }
                }
            }
        }
    }

    public static double findPath(int[][] maze, int[] start, int[] goal, double epsilon, Stack<Node> path) {
        int numRows = maze.length;
        int numCols = maze[0].length;

        double sh = heuristic(start, goal);
        Node startNode = new Node(start[0], start[1], 0, sh, null);
        OPEN.insert(startNode);

        while (!OPEN.isEmpty()) {
            Node current = OPEN.delMin();

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

                    Node neighbor = new Node(newRow, newCol, tentativeG, h, current);
                    OPEN.insert(neighbor);
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

    private static double fValue(Node s){
        return s.g + epsilonPrime * s.h;
    }

    private static Node findMinF(MinPQ<Node> open, double fGoal){
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
            }
            else return null;
        }
        else return null;
    }

    public static double findMinSum(){
        if (!OPEN.isEmpty() && !INCONS.isEmpty()) {
            return Math.min(OPEN.min().g + OPEN.min().h, INCONS.min().g + INCONS.min().h);
        }
        else if (!OPEN.isEmpty()) {
            return OPEN.min().g + OPEN.min().h;
        }
        else if (!INCONS.isEmpty()) {
            return INCONS.min().g + INCONS.min().h;
        }
        else return 0.01;
    }

    public static void publish(int[][] maze, Stack<Node> path){
        int[][] mazeCopy = new int[maze.length][maze[0].length];
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                mazeCopy[i][j] = maze[i][j];
            }
        }
        System.out.println("Path with epsilonPrime = " + lastEpsilonPrime + ", length = " + path.size() + ":");
        for (Node node : path) {
            // System.out.println("(" + node.row + ", " + node.col + ")");
            mazeCopy[node.row][node.col] = 2;
        }
        // Print the maze with the path
        for (int[] ints : mazeCopy) {
            for (int j = 0; j < mazeCopy[0].length; j++) {
                if (ints[j] == 0) {
                    System.out.print("  ");
                } else if (ints[j] == 1) {
                    System.out.print("██");
                } else if (ints[j] == 2) {
                    System.out.print("░░");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int[][] maze = {
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,1,0,0,1,0,0,0,1,1,0,1,0,1,0},
                {1,1,0,1,0,0,0,0,0,0,1,0,1,1,0},
                {1,0,0,1,0,0,1,1,1,0,1,0,0,1,0},
                {0,0,0,0,0,1,1,0,0,0,1,0,1,1,0},
                {1,0,1,1,0,0,1,0,1,0,0,0,0,0,0},
                {0,0,0,1,0,1,1,0,1,1,0,1,1,1,1},
                {1,0,0,1,1,0,0,0,0,0,0,0,0,1,0},
                {1,1,1,1,0,0,1,1,1,1,0,1,1,1,0},
                {1,0,0,0,0,1,0,0,0,1,1,0,0,1,0},
                {1,0,1,1,1,0,0,1,0,0,1,0,0,0,0},
                {0,0,0,0,0,0,1,1,0,0,1,0,1,1,0},
                {1,0,1,0,1,1,0,0,0,0,1,0,1,1,0},
                {1,0,0,0,1,0,0,1,1,1,1,0,1,1,0},
                {1,1,0,1,1,1,0,0,0,0,0,0,1,1,0}
        };

        height = maze.length;
        width = maze[0].length;
        Accessed = new Node[height][width];
        epsilon = 5;
        epsilonPrime = epsilon;
        double epsilonDecreaseFactor = 0.9;
        double minEpsilon = 1.0;
        Node start1 = new Node(0,0,0,height + width - 2,null);
        Node goal1 = new Node(height - 1,width - 1,Double.POSITIVE_INFINITY,0,null);
        Accessed[0][0] = start1;
        OPEN.insert(start1);
        Stack<Node> path = new Stack<>();
        improvePath(maze,goal1,path);
        epsilonPrime = Math.min(epsilon, goal1.g/findMinSum());
        lastEpsilonPrime = epsilonPrime;
        publish(maze,path);

        while (epsilonPrime >= minEpsilon) {
            epsilon *= epsilonDecreaseFactor;
            while(!INCONS.isEmpty())
            OPEN.insert(INCONS.delMin());
            CLOSED.clear();
            improvePath(maze,goal1,path);
            epsilonPrime = Math.min(epsilon, goal1.g/findMinSum());
            publish(maze,path);
            if (lastEpsilonPrime == 1) break;
            lastEpsilonPrime = epsilonPrime;
        }
    }
}

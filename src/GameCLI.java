import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;

public class GameCLI {

    private class FinalPathElement {
        int x;
        int y;

        public FinalPathElement(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private int epsilon;
    private int[][] maze;
    private int[][] magic;
    int magicIndex = 0;
    int questionIndex = 0;
    private int[] question;
    Stack<Node> path;
    Queue<FinalPathElement> finalPath;
    private int nikeRow = 0;
    private int nikeCol = 0;

    public GameCLI(int epsilon, int[][] maze, int[][] magic, int[] question) {
        this.epsilon = epsilon;
        this.maze = maze;
        this.magic = magic;
        this.question = question;
        this.finalPath = new Queue<FinalPathElement>();
        ARAStar araStar = new ARAStar(maze, epsilon);
        path = araStar.iterate(new Node(0, 0, 0, maze.length + maze[0].length - 2, null));
        if (!path.isEmpty()) path.pop();
    }

    private void printPath() {
        System.out.println("Current time = " + epsilon + ", Path length = " + (path.size() + 1));
        System.out.print(nikeRow + " " + nikeCol + " ");
        for (Node node : path) {
            System.out.print(node.row + " " + node.col + " ");
        }
        System.out.println();
    }

    private void action() {
        // If the epsilon is equal to the epsilon of the next magic point
        if (epsilon == magic[magicIndex][0]) {
            System.out.printf("At epsilon == %d, Nike is at %d, %d\n", magic[magicIndex][0], nikeRow, nikeCol);
            // Need to check whether the magic is valid
            if (!(nikeRow == magic[magicIndex][1] && nikeCol == magic[magicIndex][2])) {
                System.out.printf("maze[%d][%d] = 1\n", magic[magicIndex][1], magic[magicIndex][2]);
                maze[magic[magicIndex][1]][magic[magicIndex][2]] = 1;
                for (Node node : path) {
                    if (node.row == magic[magicIndex][1] && node.col == magic[magicIndex][2]) {
                        // Current path was blocked by magic
                        ARAStar araStar = new ARAStar(maze, epsilon);
                        path = araStar.iterate(
                                new Node(nikeRow, nikeCol, 0, maze.length + maze[0].length - nikeRow - nikeCol, null));
                        path.pop();
                        break;
                    }
                }
            }
            if (magicIndex < magic.length - 1)
                magicIndex++;
        }

        if (epsilon == question[questionIndex]) {
            // printPath();
            if (questionIndex < question.length - 1) questionIndex++;
        }
        
        if (!path.isEmpty()) {
            // Move one step further on the path
            printPath();
            Node node = path.pop();
            nikeRow = node.row;
            nikeCol = node.col;
            if (epsilon > 1) epsilon--;
        }
    }

    public void run() {
        while (nikeCol != maze[0].length - 1 || nikeRow != maze.length - 1) {
            finalPath.enqueue(new FinalPathElement(nikeRow, nikeCol));
            action();
        }
        finalPath.enqueue(new FinalPathElement(nikeRow, nikeCol));
        System.out.println(finalPath.size());
        for (FinalPathElement finalPathElement : finalPath) {
            System.out.print(finalPathElement.x + " " + finalPathElement.y + " ");
        }
        System.out.println();
    }
}

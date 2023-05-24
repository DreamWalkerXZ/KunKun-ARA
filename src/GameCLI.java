import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

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
        path = araStar.iterate(new Node(0, 0, 0, Math.sqrt(Math.pow(maze.length - 1, 2)
                + Math.pow(maze[0].length - 1, 2)), null));
        if (!path.isEmpty())
            path.pop();
    }

    private void printPath() {
        StdOut.println(path.size() + 1);
        StdOut.print(nikeRow + " " + nikeCol + " ");
        for (Node node : path) {
            StdOut.print(node.row + " " + node.col + " ");
        }
        StdOut.println();
    }

    private void action() {
        if (magic.length > 0 && epsilon == magic[magicIndex][0]) {
            if (!(nikeRow == magic[magicIndex][1] && nikeCol == magic[magicIndex][2])) {
                maze[magic[magicIndex][1]][magic[magicIndex][2]] = 1;
                for (Node node : path) {
                    if (node.row == magic[magicIndex][1] && node.col == magic[magicIndex][2]) {
                        // Current path was blocked by magic
                        ARAStar araStar = new ARAStar(maze, epsilon);
                        path = araStar.iterate(
                                new Node(nikeRow, nikeCol, 0, Math.sqrt(Math.pow(maze.length - 1 - nikeRow, 2)
                                        + Math.pow(maze[0].length - 1 - nikeCol, 2)), null));
                        path.pop();
                        break;
                    }
                }
            }
            if (magicIndex < magic.length - 1)
                magicIndex++;
        }

        if (question.length > 0 && epsilon == question[questionIndex]) {
            printPath();
            if (questionIndex < question.length - 1)
                questionIndex++;
        }

        if (!path.isEmpty()) {
            // Move one step further on the path
            Node node = path.pop();
            nikeRow = node.row;
            nikeCol = node.col;
            if (epsilon > 1)
                epsilon--;
        }
    }

    public void run() {
        while (nikeCol != maze[0].length - 1 || nikeRow != maze.length - 1) {
            finalPath.enqueue(new FinalPathElement(nikeRow, nikeCol));
            action();
        }
        finalPath.enqueue(new FinalPathElement(nikeRow, nikeCol));
        StdOut.println(finalPath.size());
        for (FinalPathElement finalPathElement : finalPath) {
            StdOut.print(finalPathElement.x + " " + finalPathElement.y + " ");
        }
        StdOut.println();
    }
}

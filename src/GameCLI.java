import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class GameCLI {

    private int epsilon;
    private int[][] maze;
    private int[][] magic;
    int magicIndex = 0;
    int questionIndex = 0;
    private int[] question;
    Stack<Node> path;
    Queue<int[]> finalPath;
    private int nikeRow = 0;
    private int nikeCol = 0;
    private int[] goal;

    public GameCLI(int epsilon, int[][] maze, int[][] magic, int[] question) {
        this.epsilon = epsilon;
        this.maze = maze;
        this.magic = magic;
        this.question = question;
        this.finalPath = new Queue<int[]>();
        goal = new int[] { maze.length - 1, maze[0].length - 1 };
    }

    private void printPath() {
        StdOut.println(path.size());
        for (Node node : path) {
            StdOut.print(node.row + " " + node.col + " ");
        }
        StdOut.println();
    }

    private void action() {
        if (magic.length > 0 && epsilon == magic[magicIndex][0]) {
            if (!(nikeRow == magic[magicIndex][1] && nikeCol == magic[magicIndex][2])) {
                maze[magic[magicIndex][1]][magic[magicIndex][2]] = 1;
            }
            if (magicIndex < magic.length - 1)
                magicIndex++;
        }

        path = new Stack<Node>();
        AStar.findPath(maze, new int[] { nikeRow, nikeCol }, goal, epsilon, path);

        if (question.length > 0 && epsilon == question[questionIndex]) {
            printPath();
            if (questionIndex < question.length - 1)
                questionIndex++;
        }

        if (!path.isEmpty()) {
            // Remove Nike's current position from the path
            path.pop();
            // Move one step further on the path
            if (!path.isEmpty()) {
                Node node = path.pop();
                nikeRow = node.row;
                nikeCol = node.col;
                if (epsilon > 1)
                    epsilon--;
            }
        }
    }

    public void run() {
        while (nikeCol != maze[0].length - 1 || nikeRow != maze.length - 1) {
            finalPath.enqueue(new int[] { nikeRow, nikeCol });
            action();
        }
        finalPath.enqueue(new int[] { nikeRow, nikeCol });
        StdOut.println(finalPath.size());
        for (int[] finalPathElement : finalPath) {
            StdOut.print(finalPathElement[0] + " " + finalPathElement[1] + " ");
        }
        StdOut.println();
    }
}

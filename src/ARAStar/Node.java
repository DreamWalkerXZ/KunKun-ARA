package ARAStar;

public class Node implements Comparable<Node> {
    int row;
    int col;
    double g;
    double h;
    Node parent;

    public Node(int row, int col, double g, double h, Node parent) {
        this.row = row;
        this.col = col;
        this.g = g;
        this.h = h;
        this.parent = parent;
    }

    public Node(Node node) {
        this.row = node.row;
        this.col = node.col;
        this.g = node.g;
        this.h = node.h;
        this.parent = node.parent;
    }

    @Override
    public int compareTo(Node other) {
        return Double.compare(this.g + this.h, other.g + other.h);
    }

    @Override
    public boolean equals(Object other) {
        Node other2 = (Node) other;
        return this.row == other2.row && this.col == other2.col && this.g == other2.g && this.h == other2.h;
    }

    @Override
    public String toString() {
        return "row:" + row + "    col:" + col + "    g:" + g + "    h:" + h;
    }
}
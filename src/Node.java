public class Node implements Comparable<Node> {
    int row;
    int col;
    double g;
    double h;
    double f;
    Node parent;

    public Node(int row, int col, double g, double h, double f, Node parent) {
        this.row = row;
        this.col = col;
        this.g = g;
        this.h = h;
        this.f = f;
        this.parent = parent;
    }

    @Override
    public int compareTo(Node other) {
        return Double.compare(this.f, other.f);
    }
}

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
}//For the program to run with A* algorithm


class Node0 implements Comparable<Node0> {
    int row;
    int col;
    double g;
    double h;
    Node0 parent;

    public Node0(int row, int col, double g, double h, Node0 parent) {
        this.row = row;
        this.col = col;
        this.g = g;
        this.h = h;
        this.parent = parent;
    }

    public Node0(Node0 node){
        this.row = node.row;
        this.col = node.col;
        this.g = node.g;
        this.h = node.h;
        this.parent = node.parent;
    }

    @Override
    public int compareTo(Node0 other) {
        return Double.compare(this.g + this.h, other.g + other.h);
    }

    @Override
    public boolean equals(Object other){
        Node0 other2 = (Node0) other;
        return this.row == other2.row && this.col == other2.col && this.g == other2.g && this.h == other2.h;
    }

    @Override
    public String toString(){
        return "row:" + row + "    col:" +  col + "    g:" + g + "    h:" + h ;
    }
}//the implement procedure of ARA* algorithm which seems not useful in this project.

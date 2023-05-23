import java.awt.*;

public class PathComponent extends MazeComponent {

    public PathComponent(MazePoint mazePoint, Point location, int size) {
        super(mazePoint, location, size);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponents(g);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, this.getWidth(), this.getHeight());
    }

}

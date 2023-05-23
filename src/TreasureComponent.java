import java.awt.*;

public class TreasureComponent extends MazeComponent {

    public TreasureComponent(MazePoint mazePoint, Point location, int size) {
        super(mazePoint, location, size);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponents(g);
        g.setColor(Color.YELLOW);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, this.getWidth(), this.getHeight());
    }

}
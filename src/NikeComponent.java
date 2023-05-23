import java.awt.*;

public class NikeComponent extends MazeComponent {
    
    public NikeComponent(MazePoint mazePoint, Point location, int size) {
        super(mazePoint, location, size);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponents(g);
        g.setColor(Color.GREEN);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, this.getWidth(), this.getHeight());
    }
}

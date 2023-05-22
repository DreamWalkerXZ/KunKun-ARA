import javax.swing.*;
import java.awt.*;

public abstract class MazeComponent extends JComponent {

    private MazePoint mazePoint;

    protected MazeComponent(MazePoint mazePoint, Point location, int size) {
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);
        setLocation(location);
        setSize(size, size);
        this.mazePoint = mazePoint;
    }

    public MazePoint getChessboardPoint() {
        return mazePoint;
    }

    public void setChessboardPoint(MazePoint mazePoint) {
        this.mazePoint = mazePoint;
    }

    public void swapLocation(MazeComponent another) {
        MazePoint mazePoint1 = getChessboardPoint(), mazePoint2 = another.getChessboardPoint();
        Point point1 = getLocation(), point2 = another.getLocation();
        setChessboardPoint(mazePoint2);
        setLocation(point2);
        another.setChessboardPoint(mazePoint1);
        another.setLocation(point1);
    }

}

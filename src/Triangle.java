import java.awt.*;
import java.awt.geom.*;

public class Triangle
{

    private Ellipse2D.Double[] tri;

    public Triangle(ControlPoint x, ControlPoint y, ControlPoint z)
    {
        tri = new Ellipse2D.Double[3];
        tri[0] = x;
        tri[1] = y;
        tri[2] = z;
    }

    public double getX(int index)
    {
        if ((index >= 0) && (index < 6))
            return (tri[index].x);
        System.out.println("Index out of bounds in getX()");
        return (0.0);
    }

    public double getY(int index)
    {
        if ((index >= 0) && (index < 6))
            return (tri[index].y);
        System.out.println("Index out of bounds in getY()");
        return (0.0);
    }

    public void setPoint(int index, ControlPoint x)
    {
        tri[index] = x;
    }
}

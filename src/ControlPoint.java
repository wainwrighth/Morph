import java.awt.geom.Ellipse2D;

// Create control point. Each one is an Ellipse2D so extend that
class ControlPoint extends Ellipse2D.Double {

    ControlPoint(double x, double y){

        // Set the height and width of each ellipse and set the x and y values to the
        // ones passed in
        height = 10;
        width = 10;
        this.x = x;
        this.y = y;
    }
}

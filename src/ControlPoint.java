import java.awt.geom.Ellipse2D;

class ControlPoint extends Ellipse2D.Double {

    ControlPoint(double x, double y){

        height = 5;
        width = 5;
        this.x = x;
        this.y = y;
    }
}
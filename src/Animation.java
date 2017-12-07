import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;

import static java.lang.StrictMath.ceil;

class Animation extends JPanel {

    private int size;
    ControlPoint animatedPoints[][];

    Animation(ControlPoint start[][], ControlPoint end[][], double t, int size){

        this.size = size;
        super.setPreferredSize(new Dimension(500, 500));

        // Call the animate function
        animate(start, end, t, size);
    }

    ControlPoint[][] animate(ControlPoint start[][], ControlPoint end[][], double t, int size){

        // Set the size and create a new array of where the new control points should move, create a new
        // array each time the function is called
        this.animatedPoints = new ControlPoint[size + 1][size + 1];

        // Run through the length of the starting array in both directions
        for (int i = 0; i < start.length; i++) {
            for (int j = 0; j < start[i].length; j++) {

                double x, y;

                // If the points on the start and end lattice don't match, create a new x and y coordinate using formula
                // If they do equal, then that point is the end point, so set x and y as end point
                if (ceil(start[i][j].x) != ceil(end[i][j].x) && ceil(start[i][j].y) != ceil(end[i][j].y)) {
                    x = ceil(start[i][j].x + (t * (end[i][j].x - start[i][j].x)));
                    y = ceil(start[i][j].y + (t * (end[i][j].y - start[i][j].y)));

                } else {
                    x = ceil(end[i][j].x);
                    y = ceil(end[i][j].y);
                }

                // Add the new points to the animated points array
                this.animatedPoints[i][j] = new ControlPoint(x, y);
            }
        }

        return animatedPoints;
    }

    public void paintComponent(Graphics g){

        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;

        for (int i = 0; i < size + 1; i++){
            for (int j = 0; j < size + 1; j++){

                // Draw new points each time the Animation class is called
                g2d.fill(animatedPoints[i][j]);

                // Draw the new lines as animation is happening
                if ((i + 1) < (size + 1) && (j + 1) < (size + 1)){
                    g2d.draw(new Line2D.Double(animatedPoints[i][j].x + 2.5, animatedPoints[i][j].y + 2.5,
                            animatedPoints[i+1][j+1].x + 2.5, animatedPoints[i+1][j+1].y + 2.5));
                }
                if ((j + 1) < (size + 1)){
                    g2d.draw(new Line2D.Double(animatedPoints[i][j].x + 2.5, animatedPoints[i][j].y + 2.5,
                            animatedPoints[i][j+1].x + 2.5, animatedPoints[i][j+1].y + 2.5));
                }
                if ((i + 1) < (size + 1)){
                    g2d.draw(new Line2D.Double(animatedPoints[i][j].x + 2.5, animatedPoints[i][j].y + 2.5,
                            animatedPoints[i+1][j].x + 2.5, animatedPoints[i+1][j].y + 2.5));
                }
            }
        }
    }
}

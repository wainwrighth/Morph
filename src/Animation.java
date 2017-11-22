import javax.swing.*;
import java.awt.*;

class Animation extends JPanel {

    int size;
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

                // if the points on the start and end lattice dont match, create a new x coordinate
                if (start[i][j].x != end[i][j].x) {
                    x = start[i][j].x + (t * (end[i][j].x - start[i][j].x));
                } else {
                    x = end[i][j].x;
                }

                // if the points on the start and end lattice dont match, create a new y coordinate
                if (start[i][j].y != end[i][j].y) {
                    y = start[i][j].y + (t * (end[i][j].y - start[i][j].y));
                } else {
                    y = end[i][j].y;
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

                g2d.fill(animatedPoints[i][j]);
            }
        }
    }
}

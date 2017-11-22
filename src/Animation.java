import javax.swing.*;
import java.awt.*;

class Animation extends JPanel {

    int size;
    ControlPoint animatedPoints[][];

    Animation(ControlPoint start[][], ControlPoint end[][], double t, int size){

        this.size = size;
        this.animatedPoints = new ControlPoint[size + 1][size + 1];
        super.setPreferredSize(new Dimension(500, 500));

        animate(start, end, t);
    }

    void animate(ControlPoint start[][], ControlPoint end[][], double t){

        for (int i = 0; i < start.length; i++) {
            for (int j = 0; j < start[i].length; j++) {

                double x, y;

                if (start[i][j].x != end[i][j].x) {
                    x = start[i][j].x + (t * (end[i][j].x - start[i][j].x));
                } else {
                    x = end[i][j].x;
                }

                if (start[i][j].y != end[i][j].y) {
                    y = start[i][j].y + (t * (end[i][j].y - start[i][j].y));
                } else {
                    y = end[i][j].y;
                }

                this.animatedPoints[i][j] = new ControlPoint(x, y);
            }
        }
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

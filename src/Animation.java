import javax.swing.*;
import java.awt.*;

class Animation extends JPanel {

    int size;
    ControlPoint start[][];

    Animation(ControlPoint start[][], ControlPoint end[][], double t, int size){

        this.size = size;

        animate(start, end, t);
    }

    void animate(ControlPoint start[][], ControlPoint end[][], double t){

        for (int i = 0; i < start.length; i++){
            for (int j = 0; j < start[i].length; j++) {

                if (start[i][j].x != end[i][j].x && start[i][j].y != end[i][j].y) {

                    double x = start[i][j].x + t * (end[i][j].x - start[i][j].x);
                    double y = start[i][j].y + t * (end[i][j].y - start[i][j].y);

                    start[i][j] = new ControlPoint(x, y);
                    this.start[i][j] = start[i][j];
                }
            }
        }
    }

    public void paintComponent(Graphics g){

        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;

        for (int i = 0; i < size + 1; i++){
            for (int j = 0; j < size + 1; j++){

                g2d.fill(start[i][j]);
            }
        }
    }
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;

class Lattice extends JPanel{

    ControlPoint controlPoint;
    ControlPoint points[][];
    private int size;
    boolean draggingControlPoint = false;
    int pointI, pointJ;

    Lattice(int size, MouseListener ML, MouseMotionListener MML){

        this.size = size;
        super.setPreferredSize(new Dimension(500, 500));
        super.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));

        super.addMouseListener(ML);
        super.addMouseMotionListener(MML);

        points = new ControlPoint[size + 1][size + 1];

        for (int i = 0; i < size + 1; i++){
            for (int j = 0; j < size + 1; j++){

                double x = i * size * 5;
                double y = j * size * 5;

                controlPoint = new ControlPoint(x, y);
                points[i][j] = controlPoint;
            }
        }
    }

    public void paintComponent(Graphics g){

        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;

        for (int i = 0; i < size + 1; i++) {
            for (int j = 0; j < size + 1; j++) {

                g2d.fill(points[i][j]);

                if ((i + 1) < (size + 1)){
                    g2d.draw(new Line2D.Double(points[i][j].x + 2.5, points[i][j].y + 2.5,
                                               points[i+1][j].x + 2.5, points[i+1][j].y + 2.5));
                }
                if ((i + 1) < (size + 1) && (j + 1) < (size + 1)){
                    g2d.draw(new Line2D.Double(points[i][j].x + 2.5, points[i][j].y + 2.5,
                                               points[i+1][j+1].x + 2.5, points[i+1][j+1].y + 2.5));
                }
                if ((j + 1) < (size + 1)){
                    g2d.draw(new Line2D.Double(points[i][j].x + 2.5, points[i][j].y + 2.5,
                                               points[i][j+1].x + 2.5, points[i][j+1].y + 2.5));
                }

            }
        }
    }
}

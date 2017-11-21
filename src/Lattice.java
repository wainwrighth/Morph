import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;

class Lattice extends JPanel{

    Ellipse2D.Double controlPoint;
    Ellipse2D.Double points[][];
    int size;
    boolean draggingControlPoint = false;
    int pointI, pointJ;
    boolean changeColor;

    Lattice(int size, MouseListener ML, MouseMotionListener MML){

        this.size = size;
        super.setPreferredSize(new Dimension(500, 500));
        super.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));

        super.addMouseListener(ML);
        super.addMouseMotionListener(MML);

        points = new Ellipse2D.Double[size + 1][size + 1];

        for (int i = 0; i < size + 1; i++){
            for (int j = 0; j < size + 1; j++){

                int x = i * size * 5;
                int y = j * size * 5;

                controlPoint = new Ellipse2D.Double(x, y, 5, 5);
                points[i][j] = controlPoint;
            }
        }
    }

    public void paintComponent(Graphics g){

        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;

        for (int i = 0; i < size + 1; i++) {
            for (int j = 0; j < size + 1; j++) {

                g2d.setColor(Color.BLACK);
                g2d.fill(points[i][j]);
            }
        }
    }
}

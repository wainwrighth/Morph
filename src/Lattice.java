import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;

class Lattice extends JPanel implements MouseListener, MouseMotionListener{

    private Ellipse2D.Double controlPoint;
    private Ellipse2D.Double points[][];
    private int size;
    boolean draggingControlPoint = false;
    int pointI, pointJ;

    Lattice(int size){

        this.size = size;
        super.setPreferredSize(new Dimension(500, 500));
        super.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));

        super.addMouseListener(this);
        super.addMouseMotionListener(this);

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
                g2d.fill(points[i][j]);
            }
        }

//        g2d.fill(controlPoint);
    }

    @Override
    public void mousePressed(MouseEvent e) {

        for (int i = 0; i < size + 1; i++) {
            for (int j = 0; j < size + 1; j++) {

                if (points[i][j].contains(e.getPoint())) {
                    draggingControlPoint = true;

                    pointI = i;
                    pointJ = j;
                }
            }
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {

        draggingControlPoint = false;
    }

    @Override
    public void mouseDragged(MouseEvent e) {

        int CPx, CPy;

        if (draggingControlPoint){
            CPx = e.getX();
            CPy = e.getY();

            controlPoint = new Ellipse2D.Double(CPx, CPy, 5, 5);
            points[pointI][pointJ] = controlPoint;

            repaint();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }
    @Override
    public void mouseExited(MouseEvent e) {

    }
    @Override
    public void mouseMoved(MouseEvent e) {

    }
    @Override
    public void mouseClicked(MouseEvent e) {

    }
}

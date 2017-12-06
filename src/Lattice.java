import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class Lattice extends JPanel{

    // Create a single point and an array of points
    ControlPoint controlPoint;
    ControlPoint points[][];
    private int size;
    boolean draggingControlPoint = false;
    int pointI, pointJ;
    private BufferedImage img = null;

    Lattice(int size, MouseListener ML, MouseMotionListener MML){

        // Set the size, panel size, and border of the lattice panel
        this.size = size;
        super.setPreferredSize(new Dimension(500, 500));
        super.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));

        // Add mouse listeners to the panel
        super.addMouseListener(ML);
        super.addMouseMotionListener(MML);

        // Initialize the points array
        points = new ControlPoint[size + 1][size + 1];

        // Run through the size of what is being requested
        for (int i = 0; i < size + 1; i++){
            for (int j = 0; j < size + 1; j++){

                // Calculate the x and y value for where each point should go
                double x = (i * 500) / size;
                double y = (j * 500) / size;

                // Create that Control point and add it to the array of points
                controlPoint = new ControlPoint(x, y);
                points[i][j] = controlPoint;
            }
        }
    }

    public void paintComponent(Graphics g){

        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;

        g.drawImage(img, 0, 0, null);

        // Loop through to draw each point
        for (int i = 0; i < size + 1; i++) {
            for (int j = 0; j < size + 1; j++) {

                // Draw the points
                g2d.fill(points[i][j]);

                // Draw a line from the top left corner to each adjacent point
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

    void setImage(String image){
        try {
            img = ImageIO.read(new File(image));

//            int width = img.getWidth();
//            int height = img.getHeight();
//            int[][] result = new int[width][height];

            super.removeAll();
            super.revalidate();
            super.repaint();
        }catch (IOException | NullPointerException e){ System.out.println("Error"); }
    }
}

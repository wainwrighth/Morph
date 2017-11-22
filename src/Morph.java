/* Names: John Miller, Harrison Wainwright
 * Date: 8 November 2017
 * Class: CS 335 - 001
 * Project: Morph
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class Morph extends JFrame implements ActionListener, MouseListener, MouseMotionListener {

    // Create two frames for the images
    private Lattice startLattice, endLattice;
    private int size = 10;

    private Morph(){

        super("Morph");

        // Set the Lattice frames and add them to the content pane
        createMenu();
        startLattice = new Lattice(size, this, this);
        endLattice = new Lattice(size, this, this);

        Container c = getContentPane();

        c.add(startLattice, BorderLayout.WEST);
        c.add(endLattice, BorderLayout.EAST);

        // Create Frame
        setSize(1010, 550);
        setResizable(false);
        setVisible(true);
    }

    private void createMenu(){

        // Add gameMenu to the menu bar
        JMenuBar bar = new JMenuBar();
        setJMenuBar(bar);

        // Create File tab
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic('F');

        JMenuItem newStartFile = new JMenuItem("New Start Image");
        newStartFile.setMnemonic('B');
        fileMenu.add(newStartFile);

        JMenuItem newEndFile = new JMenuItem("New End Image");
        newEndFile.setMnemonic('E');
        fileMenu.add(newEndFile);

        fileMenu.addSeparator();

        JMenuItem exitMorph = new JMenuItem("Exit");
        exitMorph.setMnemonic('X');
        exitMorph.addActionListener(this);
        fileMenu.add(exitMorph);

        JMenu morph = new JMenu("Morph");
        fileMenu.setMnemonic('M');

        JMenuItem newMorph = new JMenuItem("Start Morph");
        newMorph.setMnemonic('S');
        newMorph.addActionListener(this);
        morph.add(newMorph);

        bar.add(fileMenu);
        bar.add(morph);
    }

    public void actionPerformed(ActionEvent e){

        // Execute action listeners that are created
        if (e.getActionCommand().equals("Exit")) { System.exit(0); }
        else if (e.getActionCommand().equals("Start Morph")) { showAnimateFrame(); }
    }

    private void showAnimateFrame(){

        // Create a timer and name the animation frame
        JFrame animateFrame = new JFrame("Animation");
        Timer animateTimer;

        int min = 0;
        int max = 1;
        Random rand = new Random();

        // set a t value and create an animate object
        double t = min + rand.nextInt(max);
        Animation animate = new Animation(startLattice.points, endLattice.points, t, size);

        // Start an action listener for the timer to show the animation
        ActionListener showAnimation = e -> {

            // call animate each time to calculate a new point
            double newT = min + rand.nextInt(max);
            startLattice.points = animate.animate(startLattice.points, endLattice.points, newT, size);

            revalidate();
            repaint();
        };

        // Create timer and start it
        animateTimer = new Timer(5, showAnimation);
        animateTimer.setRepeats(true);
        animateTimer.start();

        // Create animation frame
        animateFrame.add(animate);
        animateFrame.setSize(500, 500);
        animateFrame.setVisible(true);
    }

    @Override
    public void mousePressed(MouseEvent e) {

        // When the mouse is pressed
        for (int i = 0; i < size + 1; i++) {
            for (int j = 0; j < size + 1; j++) {

                // If the point is in the start lattice, get the i and j value of the point
                if (startLattice.points[i][j].contains(e.getPoint())) {
                    startLattice.draggingControlPoint = true;

                    startLattice.pointI = i;
                    startLattice.pointJ = j;
                }

                // If the point is in the end lattice, get the i and j value of the point
                if (endLattice.points[i][j].contains(e.getPoint())) {
                    endLattice.draggingControlPoint = true;

                    endLattice.pointI = i;
                    endLattice.pointJ = j;
                }
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

        // When the mouse is released, set dragging control point boolean to false
        startLattice.draggingControlPoint = false;
        endLattice.draggingControlPoint = false;
    }

    @Override
    public void mouseDragged(MouseEvent e) {

        // When the mouse is dragged, get the source for which panel it was in
        int CPx, CPy;
        startLattice = (Lattice)e.getSource();
        endLattice = (Lattice)e.getSource();

        // If the start lattice is being dragged
        if (startLattice.draggingControlPoint){
            CPx = e.getX();
            CPy = e.getY();

            // Draw and create a new control point with the new x and y value calculated
            startLattice.controlPoint = new ControlPoint(CPx, CPy);
            startLattice.points[startLattice.pointI][startLattice.pointJ] = startLattice.controlPoint;
        }

        // If the end lattice is being dragged
        if (endLattice.draggingControlPoint){
            CPx = e.getX();
            CPy = e.getY();

            // Draw and create a new control point with the new x and y value calculated
            endLattice.controlPoint = new ControlPoint(CPx, CPy);
            endLattice.points[endLattice.pointI][endLattice.pointJ] = endLattice.controlPoint;
        }

        // Repaint the panel
        repaint();
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

    public static void main(String args[]){

        Morph M = new Morph();
        M.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){System.exit(0);}
        });
    }
}

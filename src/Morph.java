/* Names: John Miller, Harrison Wainwright
 * Date: 8 November 2017
 * Class: CS 335 - 001
 * Project: Morph
 */

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class Morph extends JFrame implements ActionListener, MouseListener, MouseMotionListener {

    // Create two frames for the images
    private Lattice startLattice, endLattice;
    private int size;

    private Morph(int size){

        super("Morph");
        this.size = size;

        // Set the Lattice frames and add them to the content pane
        createMenu();
        startLattice = new Lattice(size, this, this);
        endLattice = new Lattice(size, this, this);

        Container c = getContentPane();

        c.add(startLattice, BorderLayout.WEST);
        c.add(endLattice, BorderLayout.EAST);

        // Create Frame
        setSize(1010, 550);
//        setResizable(false);
        setVisible(true);
    }

    private void createMenu(){

        // Add gameMenu to the menu bar
        JMenuBar bar = new JMenuBar();
        setJMenuBar(bar);

        // Create File tab
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic('F');

        JFileChooser file1 = new JFileChooser();
        JFileChooser file2 = new JFileChooser();

        FileNameExtensionFilter filters = new FileNameExtensionFilter("JPEG/JPG, PNG", "jpg", "jpeg", "png");
        file1.setFileFilter(filters);
        file2.setFileFilter(filters);

        // Create new image for start lattice
        JMenuItem newStartFile = new JMenuItem("New Start Image");
        newStartFile.addActionListener(e -> {
            int returnVal = file1.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION){
                startLattice.setImage(file1.getSelectedFile().getPath());
            }
        });
        fileMenu.add(newStartFile);

        // Create new image for end lattice
        JMenuItem newEndFile = new JMenuItem("New End Image");
        newEndFile.addActionListener(e -> {
            int returnVal = file2.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION){
                endLattice.setImage(file2.getSelectedFile().getPath());
            }
        });
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

        morph.addSeparator();

        JMenuItem increaseSize = new JMenuItem("Increase Size");
        increaseSize.setMnemonic('+');
        increaseSize.addActionListener(this);
        morph.add(increaseSize);

        JMenuItem decreaseSize = new JMenuItem("Decrease Size");
        decreaseSize.setMnemonic('-');
        decreaseSize.addActionListener(this);
        morph.add(decreaseSize);

        bar.add(fileMenu);
        bar.add(morph);
    }

    public void actionPerformed(ActionEvent e){

        // Execute action listeners that are created
        if (e.getActionCommand().equals("Exit")) { System.exit(0); }
        else if (e.getActionCommand().equals("Start Morph")) { showAnimateFrame(); }

        else if (e.getActionCommand().equals("Increase Size")){

            System.out.println("Increase");
            if (size >= 10) {
                dispose();
                size += 5;

                System.out.println(size);

                Morph M = new Morph(size);
                M.addWindowListener(new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        System.exit(0);
                    }
                });
            }
        }
        else if (e.getActionCommand().equals("Decrease Size")){

            System.out.println("Decrease");
            if (size >= 15) {
                dispose();
                size -= 5;

                System.out.println(size);

                Morph M = new Morph(size);
                M.addWindowListener(new WindowAdapter(){
                    public void windowClosing(WindowEvent e){System.exit(0);}
                });
            }
        }
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

        int size = 10;

        Morph M = new Morph(size);
        M.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){System.exit(0);}
        });
    }
}

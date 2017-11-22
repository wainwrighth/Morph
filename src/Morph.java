/* Names: John Miller, Harrison Wainwright
 * Date: 8 November 2017
 * Class: CS 335 - 001
 * Project: Morph
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Morph extends JFrame implements ActionListener, MouseListener, MouseMotionListener {

    private Lattice startLattice, endLattice;
    private int size = 10;

    private Morph(){

        super("Morph");

        createMenu();
        startLattice = new Lattice(size, this, this);
        endLattice = new Lattice(size, this, this);

        Container c = getContentPane();

        c.add(startLattice, BorderLayout.WEST);
        c.add(endLattice, BorderLayout.EAST);

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

        if (e.getActionCommand().equals("Exit")) { System.exit(0); }
        else if (e.getActionCommand().equals("Start Morph")) { showAnimateFrame(); }
    }

    private void showAnimateFrame(){

        JFrame animateFrame = new JFrame("Animation");

        Timer animateTimer;

        double t = 0.50;
        Animation animate = new Animation(startLattice.points, endLattice.points, t, size);

        ActionListener showAnimation = e -> {

            for (int i = 0; i < 1000; i++) {
                double newT = 0.6;
                animate.animate(startLattice.points, endLattice.points, newT);
            }
            repaint();
        };
        animateTimer = new Timer(5, showAnimation);
        animateTimer.setRepeats(true);
        animateTimer.start();

        animateFrame.add(animate);
        animateFrame.setSize(500, 500);
        animateFrame.setVisible(true);
    }

    @Override
    public void mousePressed(MouseEvent e) {

        for (int i = 0; i < size + 1; i++) {
            for (int j = 0; j < size + 1; j++) {

                if (startLattice.points[i][j].contains(e.getPoint())) {
                    startLattice.draggingControlPoint = true;

                    startLattice.pointI = i;
                    startLattice.pointJ = j;
                }

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

        startLattice.draggingControlPoint = false;
        endLattice.draggingControlPoint = false;
    }

    @Override
    public void mouseDragged(MouseEvent e) {

        int CPx, CPy;
        startLattice = (Lattice)e.getSource();
        endLattice = (Lattice)e.getSource();

        if (startLattice.draggingControlPoint){
            CPx = e.getX();
            CPy = e.getY();

            startLattice.controlPoint = new ControlPoint(CPx, CPy);
            startLattice.points[startLattice.pointI][startLattice.pointJ] = startLattice.controlPoint;
        }

        if (endLattice.draggingControlPoint){
            CPx = e.getX();
            CPy = e.getY();

            endLattice.controlPoint = new ControlPoint(CPx, CPy);
            endLattice.points[endLattice.pointI][endLattice.pointJ] = endLattice.controlPoint;
        }

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

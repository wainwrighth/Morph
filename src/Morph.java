/* Names: John Miller, Harrison Wainwright
 * Date: 8 November 2017
 * Class: CS 335 - 001
 * Project: Morph
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;

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

        JMenuItem newFile = new JMenuItem("New File");
        newFile.setMnemonic('N');
        fileMenu.add(newFile);

        fileMenu.addSeparator();

        JMenuItem exitMorph = new JMenuItem("Exit");
        exitMorph.setMnemonic('X');
        exitMorph.addActionListener(this);
        fileMenu.add(exitMorph);

        bar.add(fileMenu);
    }

    public void actionPerformed(ActionEvent e){

        if (e.getActionCommand().equals("Exit")){ System.exit(0); }
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

            startLattice.controlPoint = new Ellipse2D.Double(CPx, CPy, 5, 5);
            startLattice.points[startLattice.pointI][startLattice.pointJ] = startLattice.controlPoint;
        }

        if (endLattice.draggingControlPoint){
            CPx = e.getX();
            CPy = e.getY();

            endLattice.controlPoint = new Ellipse2D.Double(CPx, CPy, 5, 5);
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

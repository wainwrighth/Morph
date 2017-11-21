/* Names: John Miller, Harrison Wainwright
 * Date: 8 November 2017
 * Class: CS 335 - 001
 * Project: Morph
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Morph extends JFrame implements ActionListener {

    private Lattice startLattice, endLattice;

    private Morph(){

        super("Morph");

        int size = 10;

        createMenu();
        startLattice = new Lattice(size);
        endLattice = new Lattice(size);

        startLattice.setDoubleBuffered(true);
        endLattice.setDoubleBuffered(true);

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

        bar.add(fileMenu);
    }

    public void actionPerformed(ActionEvent e){

    }

    public static void main(String args[]){

        Morph M = new Morph();
        M.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){System.exit(0);}
        });
    }
}

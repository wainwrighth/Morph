/* Names: John Miller, Harrison Wainwright
 * Date: 8 November 2017
 * Class: CS 335 - 001
 * Project: Morph
 */

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;

public class Morph extends JFrame implements ActionListener{

    // Create two frames for the images
    private Lattice startLattice;
    private Lattice endLattice;
    private int size;
    Timer animateTimer;
    boolean same;
    double t = 0.001;

    private Morph(int size){

        super("Morph");
        this.size = size;

        // Set the Lattice frames and add them to the content pane
        createMenu();
        startLattice = new Lattice(size);
        endLattice = new Lattice(size);

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

        Animation animate = new Animation(startLattice.points, endLattice.points, t, size);

        // Start an action listener for the timer to show the animation
        ActionListener showAnimation = e -> {

            same = true;

            for (int i = 0; i < size; i++){
                for (int j = 0; j < size; j++){
                    if (!(animate.animatedPoints[i][j].x == endLattice.points[i][j].x &&
                            animate.animatedPoints[i][j].y == endLattice.points[i][j].y)){

                        same = false;
                    }
                }
            }

            if (same){
                animateTimer.stop();
            }else{
                t += 0.005;
                animate.animatedPoints = animate.animate(animate.animatedPoints, endLattice.points, t, size);
            }

            animate.revalidate();
            animate.repaint();
    };

        // Create timer and start it
        animateTimer = new Timer(33, showAnimation);
        animateTimer.setRepeats(true);
        animateTimer.start();

        // Create animation frame
        animateFrame.add(animate);
        animateFrame.setSize(500, 500);
        animateFrame.setVisible(true);
    }

    public static void main(String args[]){

        int size = 10;

        Morph M = new Morph(size);
        M.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){System.exit(0);}
        });
    }
}

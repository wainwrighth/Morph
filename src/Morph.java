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
    MorphTools MorphTool = new MorphTools(); //////////////////////////////
    JFileChooser file1, file2; //////////////////////////////

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

        file1 = new JFileChooser();
        file2 = new JFileChooser();

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

            // If the size is 10 or bigger, increase by 5 when the increase button is clicked
            if (size >= 10) {
                dispose();
                size += 5;

                Morph M = new Morph(size);
                M.addWindowListener(new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        System.exit(0);
                    }
                });
            }
        }
        else if (e.getActionCommand().equals("Decrease Size")){

            // If the size is greater than 15 then decrease the size by 5
            if (size >= 15) {
                dispose();
                size -= 5;

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

        // Start the animation
        Animation animate = new Animation(startLattice.points, endLattice.points, t, size);
        animate.setImage(file1.getSelectedFile().getPath()); /////////////////////////////////////////////

        // Start an action listener for the timer to show the animation
        ActionListener showAnimation = e -> {

            same = true;

            // Run nested for loop to go through all the points
            for (int i = 0; i < size; i++){
                for (int j = 0; j < size; j++){

                    // Check if the x and y points of the animating frame match the end frame
                    // if they dont equal then set the boolean to false
                    if (!(animate.animatedPoints[i][j].x == endLattice.points[i][j].x &&
                            animate.animatedPoints[i][j].y == endLattice.points[i][j].y)){

                        same = false;
                    }
                }
            }

            // If the points are the same, stop the timer
            // Else call animate again with a new t value each time
            if (same){
                animateTimer.stop();
            }else{
                t += 0.005;
                animate.animatedPoints = animate.animate(animate.animatedPoints, endLattice.points, t, size);

                /////////////////////////////////
                for(int i = 0; i < this.size; i++)
                {
                    for(int j = 0; j < this.size; j++)
                    {
                        MorphTool.warpTriangle(startLattice.img, endLattice.img, startLattice.triangles[i][j][0], endLattice.triangles[i][j][0], null, null);
                        MorphTool.warpTriangle(startLattice.img, endLattice.img, startLattice.triangles[i][j][1], endLattice.triangles[i][j][1], null, null);
                    }
                }
                /////////////////////////////////
            }

            // Repaint the Panel
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
/* Names: John Miller, Harrison Wainwright
 * Date: 8 November 2017
 * Class: CS 335 - 001
 * Project: Morph
 */

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Morph extends JFrame implements ActionListener {

    private Morph(){

        super("Morph");

        createMenu();

        setSize(500, 500);
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

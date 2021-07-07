/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import java.awt.HeadlessException;
import javax.swing.JFrame;

/**
 *
 * @author losga
 */
public class mainFrame extends JFrame {

    public mainFrame() throws HeadlessException {
        super("Snake By Jose Alfredo");
        add(new SnakePanel());
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);

    }

    public static void main(String[] args) {
        new mainFrame();
    }
}

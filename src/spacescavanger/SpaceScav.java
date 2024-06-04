/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package spacescavanger;

import javax.swing.JFrame;

/**
 *
 * @author OscBurT21
 */
public class SpaceScav {

    /**
     * @param args the command line arguments
     */
    public SpaceScav() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setResizable(false);
//        frame.setUndecorated(true);
        frame.add(new GamePanel());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    public static void main(String[] args) {
        new SpaceScav();
    }
    
}

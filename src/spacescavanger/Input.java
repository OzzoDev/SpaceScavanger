/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spacescavanger;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

/**
 *
 * @author OscBurT21
 */
public class Input implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {

    GamePanel gp;
    private int NUM_KEYS = 255;
    private int NUM_BUTTONS = 10;
    private int mouseX = 0;
    private int mouseY = 0;
    private int scroll = 0;
    private boolean[] keys = new boolean[NUM_KEYS];
    private boolean[] keysLast = new boolean[NUM_KEYS];
    
    private boolean[] buttons = new boolean[NUM_BUTTONS];
    private boolean[] buttonsLast = new boolean[NUM_BUTTONS];

    public Input(GamePanel gamePanel) {
        this.gp = gamePanel;
        gamePanel.setFocusable(true);
        gamePanel.requestFocus();
        gamePanel.addKeyListener(this);
        gamePanel.addMouseListener(this);
        gamePanel.addMouseMotionListener(this);
        gamePanel.addMouseWheelListener(this);
    }
    
    //i varje uppdateringsintervall uppdateras den senatse listan till den nuvaranden
    //listan
    public void update() {
        scroll = 0;
        System.arraycopy(keys, 0, keysLast, 0, NUM_KEYS);
        System.arraycopy(buttons, 0, buttonsLast, 0, NUM_BUTTONS);
    }
    
    //kollar om en knapp är nedtryckt
    //retunerar sant om tryckt annars falskt
    public boolean isKey(int keyCode){
        return keys[keyCode];
    }
    
    //kollar om en knapp är nedtryckt
    //retunerar sant om tryckt, men inte under förra uppdateringen annars falskt
    public boolean isKeyDown(int keyCode){
        return keys[keyCode] && !keysLast[keyCode];
    }
    
    //kollar om en knapp är nedtryckt
    //retunerar sant om släppt, men under förra uppdateringen tryckt annars falskt
    public boolean isKeyUp(int keyCode){
        return !keys[keyCode] && keysLast[keyCode];
    }
    
    //kollar om en musknapp är nedtryckt
    //retunerar sant om tryckt annars falskt
    public boolean isButton(int button){
        return buttons[button];
    }
    
    //kollar om en musknapp är nedtryckt
    //retunerar sant om tryckt, men inte under förra uppdateringen annars falskt
    public boolean isButtonDown(int button){
        return buttons[button] && !buttonsLast[button];
    }
    
    //kollar om en knapp är nedtryckt
    //retunerar sant om släppt, men under förra uppdateringen tryckt annars falskt
    public boolean isButtonUp(int button){
        return !buttons[button] && buttonsLast[button];
    }
    

    public int getMouseX() {
        return mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }

    public int getScroll() {
        return scroll;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        buttons[e.getButton()] = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        buttons[e.getButton()] = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        //eventuellt ingen skala
        mouseX = (int)(e.getX());
        mouseY = (int)(e.getY());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        //eventuellt ingen skala
        mouseX = (int)(e.getX());
        mouseY = (int)(e.getY());
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        scroll = e.getWheelRotation();
    }
}

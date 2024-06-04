/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package spacescavanger.gameobjects;

import java.awt.Graphics2D;

/**
 *
 * @author OscBurT21
 */
public interface GraphicEffects {
    
    public void update();
    
    public void render(Graphics2D g);
    
    public boolean isDead();
    
}

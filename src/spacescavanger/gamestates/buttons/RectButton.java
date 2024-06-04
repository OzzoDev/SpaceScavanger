/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spacescavanger.gamestates.buttons;

import java.awt.Rectangle;
import java.awt.geom.Area;
import spacescavanger.GamePanel;

/**
 *
 * @author OscBurT21
 */
public class RectButton extends Button{
    
    public RectButton(GamePanel gp, int x, int y, Rectangle a, String text) {
        super(gp, x, y, new Area(a), text);
    }
}

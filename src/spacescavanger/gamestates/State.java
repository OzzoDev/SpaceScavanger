/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package spacescavanger.gamestates;

import java.awt.Graphics2D;
import java.util.ArrayList;
import spacescavanger.gameobjects.Resource;

/**
 *
 * @author OscBurT21
 */
public interface State {
    
    public void update();
    
    public void render(Graphics2D g2d);
    
    public GameState getGameState();

    
}

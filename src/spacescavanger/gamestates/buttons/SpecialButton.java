/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spacescavanger.gamestates.buttons;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import spacescavanger.GamePanel;

/**
 *
 * @author OscBurT21
 */
public class SpecialButton extends Button {

    public SpecialButton(GamePanel gp, int x, int y, String text) {
        Shape s = new Ellipse2D.Double(0, 0, (int)(32*gp.scaleX), (int)(32*gp.scaleY));
        this.gp = gp;
        a = new Area(s);
        localTrans.translate(x, y);
        a.transform(localTrans);
        this.x = x;
        this.y = y;
        this.text = text;
        centerX = a.getBounds().getCenterX();
        centerY = a.getBounds().getCenterY();
        baseCol = Color.orange;
        edgeCol = Color.black;
        textCol = Color.darkGray;
        try {
            BufferedImage[] sprites = gp.spriteSheetLoader(32, 32, 5, 1, "buttons/testbutton.png");
            normalImg = new BufferedImage[1];
            normalImg[0] = sprites[0];
            pressedImg = new BufferedImage[1];
            pressedImg[0] = sprites[4];
            hoverImg = new BufferedImage[8];
            for (int i = 0; i < sprites.length; i++) {
                hoverImg[i] = sprites[i];
            }
            hoverImg[5] = sprites[3];
            hoverImg[6] = sprites[2];
            hoverImg[7] = sprites[1];
            currentImg = normalImg;
        } catch (IOException ex) {
            Logger.getLogger(SpecialButton.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spacescavanger.gamestates.buttons;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import spacescavanger.GamePanel;

/**
 *
 * @author OscBurT21
 */
public class StationButton extends Button {

    public StationButton(GamePanel gp, int x, int y, String text) {
        this.gp = gp;
        this.x = x;
        this.y = y;
        this.text = text;
        double sx = gp.scaleX;
        double sy = gp.scaleY;
        int[] xp = {(int) (10 * sx), (int) (190 * sx), (int) (190 * sx), (int) (180 * sx), (int) (10 * sx)};
        int[] yp = {(int) (10 * sy), (int) (10 * sy), (int) (40 * sy), (int) (50 * sy), (int) (50 * sy)};
        Polygon p = new Polygon(xp, yp, xp.length);
        a = new Area(p);
        localTrans.translate(x, y);
        a.transform(localTrans);
        normalImg = new BufferedImage[1];
        hoverImg = new BufferedImage[1];
        pressedImg = new BufferedImage[1];
        currentImg = new BufferedImage[1];
        normalImg[0] = gp.graphics.stButNorm;
        hoverImg[0] = gp.graphics.stButHover;
        pressedImg[0] = gp.graphics.stButPressed;
        currentImg = normalImg;
        textCol = new Color(34, 54, 240);
    }

    @Override
    public void update() {
        if (isHit(gp.input.getMouseX(), gp.input.getMouseY()) && gp.input.isButton(1)) {
            pressed = true;
            hover = false;
            currentImg = pressedImg;
            textCol = textCol.darker();
        } else if (isHit(gp.input.getMouseX(), gp.input.getMouseY())) {
            hover = true;
            pressed = false;
            currentImg = hoverImg;
             textCol = new Color(250, 10, 240).darker().darker();
        } else {
            hover = false;
            pressed = false;
            currentImg = normalImg;
            textCol = new Color(34, 54, 240);
        }
    }

    @Override
    public void render(Graphics2D g) {
        if (currentImg != null) {
            //rita bild
            g.drawImage(currentImg[0], (int) localTrans.getTranslateX(), (int) localTrans.getTranslateY(),
                    (int) (currentImg[0].getWidth() * gp.scaleX), (int) (currentImg[0].getHeight() * gp.scaleY), null);
            g.setFont(gp.graphics.axaxax_small);
            int stringWidth = g.getFontMetrics().stringWidth(text);
            int stringHeight = g.getFontMetrics().getHeight();
            g.setColor(textCol);
            g.drawString(text, (int) (localTrans.getTranslateX() + 30 * gp.scaleX), (int) (localTrans.getTranslateY()+40 * gp.scaleY));
        }
    }
}

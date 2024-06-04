/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spacescavanger.gamestates.buttons;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import spacescavanger.GamePanel;

/**
 *
 * @author OscBurT21
 */
public abstract class Button {

    protected GamePanel gp;

    //insättningspunkt
    protected int x;
    protected int y;
    protected int imageCounter;
    protected int imageTimer;
    protected int imageDelay = 8;

    protected double centerX;
    protected double centerY;

    protected Point textPos = null;

    protected boolean hover = false;
    protected boolean pressed = false;

    protected Color baseCol;
    protected Color edgeCol;
    protected Color textCol;

    protected String text;

    protected BufferedImage[] normalImg;
    protected BufferedImage[] hoverImg;
    protected BufferedImage[] pressedImg;
    protected BufferedImage[] currentImg;

    protected Area a;
    protected BasicStroke stroke = new BasicStroke();
    protected AffineTransform localTrans = new AffineTransform();

    public Button(GamePanel gp, int x, int y, Area a, String text) {
        this.gp = gp;
        localTrans.translate(x, y);
        a.transform(localTrans);
        this.x = x;
        this.y = y;
        this.a = a;
        this.text = text;
        centerX = a.getBounds().getCenterX();
        centerY = a.getBounds().getCenterY();
        baseCol = Color.orange;
        edgeCol = Color.black;
        textCol = Color.darkGray;
    }

    public Button() {

    }

    public void update() {
        imageTimer++;
        if (imageTimer >= imageDelay) {
            imageCounter++;
            imageTimer = 0;
        }
        if (isHit(gp.input.getMouseX(), gp.input.getMouseY()) && gp.input.isButton(1)) {
            pressed = true;
            hover = false;
            currentImg = pressedImg;

        } else if (isHit(gp.input.getMouseX(), gp.input.getMouseY())) {
            hover = true;
            pressed = false;
            currentImg = hoverImg;
        } else {
            hover = false;
            pressed = false;
            currentImg = normalImg;
        }
        if (currentImg != null) {
            if (imageCounter >= currentImg.length) {
                imageCounter = 0;
            }
        }
    }

    public boolean isHit(double x, double y) {
        return a.contains(x, y);
    }

    public String getText() {
        return text;
    }

    public boolean isPressed() {
        return pressed;
    }

    public void render(Graphics2D g2d) {
        g2d.setStroke(stroke);
        g2d.setFont(gp.graphics.axaxax_small);
        //om bilden finns rita bilden
        if (currentImg != null) {
            //rita bild
            g2d.drawImage(currentImg[imageCounter], (int) localTrans.getTranslateX(), (int) localTrans.getTranslateY(),
            (int) (currentImg[imageCounter].getWidth() * gp.scaleX), (int) (currentImg[imageCounter].getHeight() * gp.scaleY), null);
        } else {
            //kolla state på knappen och rita därefter
            if (pressed) {
                g2d.setColor(edgeCol);
                g2d.fill(a);
                g2d.setColor(baseCol);
                g2d.draw(a);
                g2d.setColor(textCol);
                if (text.length() > 0) {
                    int stringWidth = g2d.getFontMetrics().stringWidth(text);
                    int stringHeight = g2d.getFontMetrics().getHeight();
                    g2d.drawString("" + text, (int) centerX - stringWidth / 2, (int) centerY+stringHeight/3);
                }
            } else if (hover) {
                g2d.setColor(baseCol.darker());
                g2d.fill(a);
                g2d.setColor(edgeCol.brighter());
                g2d.draw(a);
                g2d.setColor(textCol);
                if (text.length() > 0) {
                    int stringWidth = g2d.getFontMetrics().stringWidth(text);
                    int stringHeight = g2d.getFontMetrics().getHeight();
                    g2d.drawString("" + text, (int) centerX - stringWidth / 2, (int) centerY+stringHeight/3);
                }
            } else {
                g2d.setColor(baseCol);
                g2d.fill(a);
                g2d.setColor(edgeCol);
                g2d.draw(a);
                g2d.setColor(textCol);
                if (text.length() > 0) {
                    int stringWidth = g2d.getFontMetrics().stringWidth(text);
                    int stringHeight = g2d.getFontMetrics().getHeight();
                    g2d.drawString("" + text, (int) centerX - stringWidth / 2, (int) centerY+stringHeight/3);
                }
            }
        }
    }
}

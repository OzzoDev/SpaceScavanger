/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spacescavanger.gameobjects;

import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import spacescavanger.GamePanel;

/**
 *
 * @author OscBurT21
 */
public class Resource extends GameObject {

    private double scale = 0.6;
    private int value;
    private int timer;
    private int delay = 6;
    private int counter;
    private int pickUpDelay;
    private int pickUpTimer;
    private double cargoX;
    private double cargoY;
    private boolean delayPickUp;
    private boolean pickUp;
    private boolean sold = false;

    public Resource(GamePanel gp, int x, int y, int value) {
        this.gp = gp;
        this.x = x;
        this.y = y;
        this.value = value;
        speed = Math.random() * 2 + 0.5;
        direction = Math.random() * 360;
        rotation = 0.0;
        rotacc = Math.random() * 0.8 - 0.4;
        velVect.set(Math.cos(Math.toRadians(direction)) * speed, Math.sin(Math.toRadians(direction)) * speed);
        int iconNum = 0;
        if (value > 200) {
            iconNum = (int) (Math.random() * 2 + 2);
        } else if (value > 100) {
            iconNum = (int) (Math.random() * 4);
            if (iconNum >= 2) {
                iconNum += 2;
            }
        }
        images = gp.graphics.gems[iconNum];
        double colShapeScale = 0.7;
        double colShapeWidth = images[0].getWidth() * scale * gp.scaleX * colShapeScale;
        double colShapeHeight = images[0].getHeight() * scale * gp.scaleY * colShapeScale;
        int xp[] = {-(int) (colShapeWidth * scale), 0, (int) (colShapeWidth * scale), 0};
        int yp[] = {0, (int) (colShapeHeight * scale), 0, -(int) (colShapeHeight * scale)};
        collisionShape = new Polygon(xp, yp, xp.length);
        pickUp = true;
    }

    public Resource(GamePanel gp, int x, int y, int value, int direction, int pickUpDelay) {
        this.gp = gp;
        this.x = x;
        this.y = y;
        this.value = value;
        this.pickUpDelay = pickUpDelay;
        if (pickUpDelay != 0) {
            delayPickUp = true;
        }
        speed = Math.random() * 2 + 0.5;
        rotation = 0.0;
        rotacc = Math.random() * 0.8 - 0.4;
        velVect.set(Math.cos(Math.toRadians(direction + 180)) * speed, Math.sin(Math.toRadians(direction + 180)) * speed);
        int iconNum = 0;
        if (value > 200) {
            iconNum = (int) (Math.random() * 2 + 2);
        } else if (value > 100) {
            iconNum = (int) (Math.random() * 4);
            if (iconNum >= 2) {
                iconNum += 2;
            }
        }
        images = gp.graphics.gems[iconNum];
        double colShapeScale = 0.7;
        double colShapeWidth = images[0].getWidth() * scale * gp.scaleX * colShapeScale;
        double colShapeHeight = images[0].getHeight() * scale * gp.scaleY * colShapeScale;
        int xp[] = {-(int) (colShapeWidth * scale), 0, (int) (colShapeWidth * scale), 0};
        int yp[] = {0, (int) (colShapeHeight * scale), 0, -(int) (colShapeHeight * scale)};
        collisionShape = new Polygon(xp, yp, xp.length);
    }

    @Override
    public void update() {
        if (sold) {
            cargoX+=velVect.x;
            cargoY+=velVect.y;
            posVect.set(cargoX,cargoY);
        } else {
            x += velVect.x;
            y += velVect.y;
            x = (x + gp.worldWidth) % gp.worldWidth;
            y = (y + gp.worldHeight) % gp.worldHeight;
            posVect.set(x, y);
            rotation += rotacc;
        }
        
        animate();
        if (delayPickUp) {
            pickUpTimer++;
            if (pickUpTimer >= pickUpDelay) {
                pickUp = true;
                delayPickUp = false;
            }
        }
    }

    private void animate() {
        timer++;
        if (timer > delay) {
            counter++;
            if (counter >= images.length - 1) {
                counter = 0;
            }
            timer = 0;
        }
    }

    public void moveTo(double carX, double carY,double targetX, double targetY) {
        cargoX = carX;
        cargoY = carY;
        direction = Math.toDegrees(Math.atan2((targetY - cargoY), (targetX - cargoX)));
        speed = 6.0 * gp.scaleX;
        posVect.set(cargoX, cargoY);
        velVect.set(Math.cos(Math.toRadians(direction)) * speed, Math.sin(Math.toRadians(direction)) * speed);
        sold = true;
    }

    @Override
    public void renderObject(Graphics2D g, double posX, double posY) {
        AffineTransform old = g.getTransform();
        objectTrans = new AffineTransform();
        objectTrans.translate(posX, posY);
        objectTrans.rotate(Math.toRadians(rotation));
        g.transform(objectTrans);
        g.drawImage(images[counter], (int) (-images[0].getWidth() * scale * gp.scaleX / 2), (int) (-images[0].getHeight() * scale * gp.scaleY / 2), (int) (images[0].getWidth() * scale * gp.scaleX), (int) (images[0].getHeight() * scale * gp.scaleY), null);
        g.setTransform(old);
    }

    public void pickUp() {
        dead = true;
    }

    public int getValue() {
        return value;
    }

    public BufferedImage getImage() {
        return images[0];
    }

    public boolean isPickUp() {
        return pickUp;
    }

    public double getCargoX() {
        return cargoX;
    }

    public void setCargoX(int cargoX) {
        this.cargoX = cargoX;
    }

    public double getCargoY() {
        return cargoY;
    }

    public void setCargoY(int cargoY) {
        this.cargoY = cargoY;
    }

    public boolean isSold() {
        return sold;
    }
    public void setDead(boolean dead){
        this.dead = dead;
    }
}

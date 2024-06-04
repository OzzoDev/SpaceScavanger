/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spacescavanger.gameobjects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import spacescavanger.GamePanel;
import spacescavanger.Vector2D;

/**
 *
 * @author OscBurT21
 */
public abstract class GameObject {

    protected GamePanel gp;
    protected double x;
    protected double y;
    protected double rotation; //rotation är riktning som obejektet tittar mot
    protected double direction; //direction är riktning som objektet rör sig
    protected double maxSpeed;
    protected double speed;
    protected double acc;
    protected double decc;
    protected double rotacc;
    protected int currentImage;
    protected BufferedImage[] images = {null};
    protected Shape collisionShape;
    protected boolean hit = false;
    protected boolean dead = false;
    protected String name = "no-name";
    public AffineTransform objectTrans = new AffineTransform();
    protected Vector2D posVect = new Vector2D(0.0, 0.0);
    protected Vector2D velVect = new Vector2D(0.0, 0.0);

    public abstract void update();

    public void render(Graphics2D g) {
        int screenX = (int) (x - gp.camera.getOffX());
        int screenY = (int) (y - gp.camera.getOffY());
        renderObject(g, screenX, screenY);
        boolean nearRightEdge = screenX > gp.screenWidth - gp.tileSizeX;
        boolean nearBottomEdge = screenY > gp.screenHeight - gp.tileSizeY;
        boolean nearLeftEdge = screenX < 0;
        boolean nearTopEdge = screenY < 0;
        //rakt ut över kanterna
        if (nearRightEdge) {
            renderObject(g, screenX - gp.worldWidth, screenY);
        }
        if (nearLeftEdge) {
            renderObject(g, screenX + gp.worldWidth, screenY);
        }
        if (nearTopEdge) {
            renderObject(g, screenX, screenY + gp.worldHeight);
        }
        if (nearBottomEdge) {
            renderObject(g, screenX, screenY - gp.worldHeight);
        }
        //över kant vid hörn
        if (nearRightEdge && nearTopEdge) {
            renderObject(g, screenX - gp.worldWidth, screenY + gp.worldHeight);
        }
        if (nearRightEdge && nearBottomEdge) {
            renderObject(g, screenX - gp.worldWidth, screenY - gp.worldHeight);
        }
        if (nearLeftEdge && nearTopEdge) {
            renderObject(g, screenX + gp.worldWidth, screenY + gp.worldHeight);
        }
        if (nearLeftEdge && nearBottomEdge) {
            renderObject(g, screenX + gp.worldWidth, screenY - gp.worldHeight);
        }
    }

    public void renderObject(Graphics2D g, double posX, double posY) {
        AffineTransform old = g.getTransform();
        objectTrans = new AffineTransform();
        objectTrans.translate(posX, posY);
        objectTrans.rotate(Math.toRadians(rotation));
        g.transform(objectTrans);
        g.setColor(Color.orange);
        g.fill(collisionShape);
        g.setColor(Color.black);
        g.draw(collisionShape);
        g.setTransform(old);
    }

    public boolean checkCollision(Shape otherShape) {
        if (objectTrans.createTransformedShape(collisionShape).getBounds().intersects(otherShape.getBounds())) {
//            System.out.println("intersecting");
            Area a = new Area(objectTrans.createTransformedShape(collisionShape));
            a.intersect(new Area(otherShape));
            if (!a.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public Shape getCollisionShape() {
        return objectTrans.createTransformedShape(collisionShape);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public boolean isHit() {
        return hit;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }

    public double getRotation() {
        return rotation;
    }

    public double getDirection() {
        return direction;
    }

    public void setDirection(double direction) {
        this.direction = direction;
        velVect.set(Math.cos(Math.toRadians(direction)) * speed, Math.sin(Math.toRadians(direction)) * speed);
    }

    public boolean isDead() {
        return dead;
    }
    
    public BufferedImage[] getImages(){
        return images;
    }

    public double getMaxSpeed() {
        return maxSpeed;
    }

    public double getSpeed() {
        return speed;
    }

    public double getAcc() {
        return acc;
    }

    public double getRotacc() {
        return rotacc;
    }

    public void setPosVect(Vector2D posVect) {
        this.posVect = posVect;
    }

    public void setVelVect(Vector2D velVect) {
        this.velVect = velVect;
    }
}

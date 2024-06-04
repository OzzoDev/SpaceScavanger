/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spacescavanger.gameobjects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import spacescavanger.GamePanel;
import spacescavanger.Vector2D;

/**
 *
 * @author OscBurT21
 */
public class SpaceStation extends GameObject {

    private int width;
    private int height;
    private double scale = 0.7;
    private Point2D spawnpoint;

    public SpaceStation(GamePanel gp, int x, int y) {
        this.gp = gp;
        this.x = x;
        this.y = y;
        images = new BufferedImage[1];
        images[0] = gp.graphics.stationImage;
        width = (int) (images[0].getWidth() * gp.scaleX*scale);
        height = (int) (images[0].getHeight() * gp.scaleY*scale);
        collisionShape = new Rectangle2D.Double(50 * gp.scaleX*scale - width / 2, -60 * gp.scaleY*scale, 180 * gp.scaleX*scale, 120 * gp.scaleY*scale);
        speed = 0.03 * gp.scaleX*scale;
        rotacc = 0.008;
        direction = Math.random() * 360.0;
        velVect.set(Math.cos(Math.toRadians(direction)) * speed, Math.sin(Math.toRadians(direction)) * speed);
        spawnpoint = new Point2D.Double(100 * gp.scaleX*scale, 0.0);
        name = "Scavanger Station";
    }

    @Override
    public void update() {
        x += velVect.x;
        y += velVect.y;
        x = (x + gp.worldWidth) % gp.worldWidth;
        y = (y + gp.worldHeight) % gp.worldHeight;
        posVect.set(x, y);
        rotation += rotacc;
    }
    
    @Override 
    public void renderObject(Graphics2D g, double posX, double posY) {
        AffineTransform old = g.getTransform();
        objectTrans = new AffineTransform();
        objectTrans.translate(posX, posY);
        objectTrans.rotate(Math.toRadians(rotation));
        g.transform(objectTrans);
        g.drawImage(images[0], -width/2, -height/2, width,height,null);
        g.setColor(Color.orange);
        g.draw(collisionShape);
        g.drawOval((int)(spawnpoint.getX()-20), (int)(spawnpoint.getY()-20), 40, 40);
        g.setTransform(old);
    }
    
    public Vector2D getSpawnPoint(){
        Point2D point = objectTrans.transform(spawnpoint, null);
        Vector2D vect = new Vector2D(point.getX(), point.getY());
        return vect;
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spacescavanger.gameobjects.obstacle;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import spacescavanger.GamePanel;
import spacescavanger.gameobjects.GameObject;
import spacescavanger.gameobjects.Resource;
import spacescavanger.gameobjects.abilities.Destoryable;
import spacescavanger.gameobjects.abilities.Profit;

/**
 *
 * @author OscBurT21
 */
public class Asteroid extends GameObject implements Destoryable, Profit {

    private Color debrisCol;
    private int maxHitpoints;
    private int hitpoints;
    private int radie;
    private int value;

    public Asteroid(GamePanel gp, int x, int y, int radie) {
        this.gp = gp;
        this.x = x;
        this.y = y;
        this.radie = radie;
        collisionShape = makeRandomPolygon(radie);
        int colInt = (int) (Math.random() * 80 + 80);
        debrisCol = new Color(colInt, colInt, colInt);
        speed = Math.random() * 2 + 0.5;
        direction = Math.random() * 360;
        rotation = 0.0;
        rotacc = Math.random() * 0.8 - 0.4;
        velVect.set(Math.cos(Math.toRadians(direction)) * speed, Math.sin(Math.toRadians(direction)) * speed);
        maxHitpoints = radie + 20;
        hitpoints = maxHitpoints;
        images = new BufferedImage[1];
        images[0] = gp.graphics.crater200x200[(int) (Math.random() * gp.graphics.crater200x200.length)];
        value = radie * 3;
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
        g.setClip(collisionShape);
        g.drawImage(images[0], (int) (-collisionShape.getBounds().width * gp.scaleX / 2), (int) (-collisionShape.getBounds().height * gp.scaleY / 2), (int) (images[0].getWidth() * gp.scaleX), (int) (images[0].getHeight() * gp.scaleY), null);
        g.setClip(null);
        if (hit) {
            g.setColor(Color.red);
        } else {
            g.setColor(Color.white);
        }
//        g.setColor(Color.white);
//        g.draw(collisionShape.getBounds());
        g.setTransform(old);
        g.setColor(Color.orange);
//        int hitbarlength = 150;
//        int currHPlength = (int) ((double) hitpoints / maxHitpoints * hitbarlength);
//        g.fillRect((int) (posX - hitbarlength / 2), (int) (posY + collisionShape.getBounds().getHeight() / 2), currHPlength, 8);
//        g.setColor(Color.red);
//        g.drawRect((int) (posX - hitbarlength / 2), (int) (posY + collisionShape.getBounds().getHeight() / 2), hitbarlength, 8);
    }

    private Shape makeRandomPolygon(double size) {
        double angle = 0.0;
        double currentSize = size;
        ArrayList<Point> points = new ArrayList<>();
        while (angle < 360.0) {
            currentSize = (int) (size + Math.random() * currentSize * 0.5 - currentSize * 0.25);
            Point p = new Point((int) (Math.cos(Math.toRadians(angle)) * currentSize), (int) (Math.sin(Math.toRadians(angle)) * currentSize));
            points.add(p);
            angle = angle + Math.random() * 10 + 25;
        }
        int[] xp = new int[points.size()];
        int[] yp = new int[points.size()];
        for (int i = 0; i < points.size(); i++) {
            xp[i] = points.get(i).x;
            yp[i] = points.get(i).y;
        }
        return new Polygon(xp, yp, xp.length);
    }

    @Override
    public void destoryed() {
        int minRadie = 20;
        if (radie > minRadie) {
            for (int i = 0; i < (int) (Math.random() * 2 + 2); i++) {
                int rad = (int) (radie * (Math.random() * 0.2 + 0.3));
                if (rad > 10) {
                    Asteroid a1 = new Asteroid(gp, (int) x, (int) y, rad);
                    a1.setDirection(Math.random() * 120 - 60 + direction);
                    gp.map.addObject(a1);
                }
            }
        }
        if (radie <= minRadie) {
            if (Math.random() < 0.4) {
                int newValue = (int) (Math.random() * 180 + 80);
                generateResource(newValue);
            } else {
                generateResource(value);
            }
        } else {
            do {
                int resValue = (int) (Math.random() * value + value / 8);
                if (resValue < value / 2) {
                    generateResource(value);
                    value -= resValue;
                } else {
                    generateResource(value);
                    value = 0;
                }
            } while (value > 0);
        }
    }

    @Override
    public void takeDamage(int damage) {
        hitpoints -= damage;
        if (hitpoints <= 0) {
            dead = true;
            destoryed();
        }
    }

    public Color getDebrisCol() {
        return debrisCol;
    }

    @Override
    public void generateResource(int value) {
        Resource res = new Resource(gp, (int) x, (int) y, value);
        res.setDirection(Math.random() * 120 - 60 + direction);
        gp.map.addObject(res);
    }
}

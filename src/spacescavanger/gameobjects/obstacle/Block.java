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
import java.util.ArrayList;
import spacescavanger.GamePanel;
import spacescavanger.gameobjects.GameObject;
import spacescavanger.gameobjects.abilities.Destoryable;

/**
 *
 * @author OscBurT21
 */
public class Block extends GameObject implements Destoryable{

    Color col;
    private int maxHitpoints;
    private int hitpoints;

    public Block(GamePanel gp, int x, int y) {
        this.gp = gp;
        this.x = x;
        this.y = y;
        collisionShape = makeRandomPolygon();
        col = new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255));
        speed = Math.random() * 2 + 0.5;
        direction = Math.random() * 360;
        rotation = 0.0;
        rotacc = Math.random() * 0.8 - 0.4;
        velVect.set(Math.cos(Math.toRadians(direction)) * speed, Math.sin(Math.toRadians(direction)) * speed);
        maxHitpoints = 100;
        hitpoints = maxHitpoints;
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
        g.setColor(col);
        g.fill(collisionShape);
        g.setColor(Color.black);
        g.draw(collisionShape);
        g.drawString((int) (x / gp.tileSizeX) + ", " + (int) (y / gp.tileSizeY), 0, 0);
        g.drawString("HP: "+hitpoints, 0, 20);
        if (hit) {
            g.setColor(Color.red);
        } else {
            g.setColor(Color.white);
        }
//        g.setColor(Color.white);
//        g.draw(collisionShape.getBounds());
        g.setTransform(old);
        g.setColor(Color.orange);
        int hitbarlength = 150;
        int currHPlength = (int)((double)hitpoints/maxHitpoints * hitbarlength);
        g.fillRect((int)(posX-hitbarlength/2), (int)(posY+collisionShape.getBounds().getHeight()/2), currHPlength, 8);
        g.setColor(Color.red);
        g.drawRect((int)(posX-hitbarlength/2), (int)(posY+collisionShape.getBounds().getHeight()/2), hitbarlength, 8);
    }

    @Override
    public void render(Graphics2D g) {
        super.render(g);
    }

    private Shape makeRandomPolygon() {
        double angle = 0.0;
        double size = Math.random() * 50 + 50;
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
        System.out.println("This target is destoryed "+this.toString());
    }

    @Override
    public void takeDamage(int damage) {
        hitpoints-=damage;
        if (hitpoints<=0) {
            dead = true;
            destoryed();
        }
    }

    public Color getCol() {
        return col;
    }
}

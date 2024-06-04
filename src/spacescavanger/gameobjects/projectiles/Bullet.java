/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spacescavanger.gameobjects.projectiles;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import spacescavanger.GamePanel;
import spacescavanger.gameobjects.Explosion;
import spacescavanger.gameobjects.GameObject;
import spacescavanger.gameobjects.abilities.Delay;
import spacescavanger.gameobjects.abilities.Destoryable;
import spacescavanger.gameobjects.obstacle.Asteroid;
import spacescavanger.gameobjects.obstacle.Block;

/**
 *
 * @author OscBurT21
 */
public class Bullet extends GameObject implements Delay {

    private int damage;
    private int range;
    private int rangeCounter = 0;
    private double dx;
    private double dy;
    private boolean delayed = true;
    private int delayTimer = 0;
    private int delayDuration = 20;
    private BufferedImage bulletImage;

    public Bullet(GamePanel gp, double x, double y, double direction, double speed, int damage, int range, BufferedImage bulletImage) {
        this.gp = gp;
        this.x = x;
        this.y = y;
        this.direction = direction;
        rotation = direction;
        this.speed = speed;
        this.damage = damage;
        this.range = range;
        this.bulletImage = bulletImage;
        int[] xp = {(int) (0 * gp.scaleX), (int) (4 * gp.scaleX), (int) (6 * gp.scaleX), (int) (4 * gp.scaleX), (int) (0 * gp.scaleX)};
        int[] yp = {(int) (-1 * gp.scaleY), (int) (-1 * gp.scaleY), (int) (0 * gp.scaleY), (int) (1 * gp.scaleY), (int) (1 * gp.scaleY)};
        collisionShape = new Polygon(xp, yp, xp.length);
        objectTrans = new AffineTransform();
        dx = Math.cos(Math.toRadians(direction)) * speed;
        dy = Math.sin(Math.toRadians(direction)) * speed;
    }

    @Override
    public void update() {
        rangeCounter += speed;
        if (rangeCounter < range) {
            x += dx;
            y += dy;
            x = (x + gp.worldWidth) % gp.worldWidth;
            y = (y + gp.worldHeight) % gp.worldHeight;
        } else {
            dead = true;
        }
        if (!checkDelayed()) {
            for (GameObject go : gp.map.getGameObjects()) {
                if (go instanceof Destoryable destr) {
                    if (checkCollision(go.getCollisionShape())) {
                        destr.takeDamage(damage); //Objekt tar skaka
                        dead = true; //Döda kulan när den träffar ett annat objekt
                        if (go instanceof Asteroid) {
                            Explosion e = new Explosion(gp, x, y, 20, 6, ((Asteroid) go).getDebrisCol(), go);
                            gp.map.addEffect(e);
                        } else {
                            Explosion e = new Explosion(gp, x, y, 20, 6, null,go);
                            gp.map.addEffect(e);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void renderObject(Graphics2D g, double posX, double posY) {
        AffineTransform old = g.getTransform();
        objectTrans = new AffineTransform();
        objectTrans.translate(posX, posY);
        objectTrans.rotate(Math.toRadians(rotation));
        objectTrans.scale(1.0, 1.0);
        g.transform(objectTrans);
        g.drawImage(bulletImage, 0, (int) (-1 * bulletImage.getHeight() / 2 * gp.scaleY), (int) (bulletImage.getWidth() * gp.scaleX), (int) (bulletImage.getHeight() * gp.scaleY), null);
        g.setTransform(old);
    }

    @Override
    public boolean isDelayed() {
        return delayed;
    }

    @Override
    public boolean checkDelayed() {
        if (delayed) {
            delayTimer++;
            if (delayTimer >= delayDuration) {
                delayed = false;
            }
        }
        return delayed;
    }
}

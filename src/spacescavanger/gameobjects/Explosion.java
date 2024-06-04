/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spacescavanger.gameobjects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Shape;
import java.util.ArrayList;
import spacescavanger.GamePanel;

/**
 *
 * @author OscBurT21
 */
public class Explosion implements GraphicEffects {

    private GamePanel gp;
    private ArrayList<Particle> partiklar = new ArrayList<>();
    private boolean dead = false;

    public Explosion(GamePanel gp, double x, double y, int antalPartiklar, int maxPartikelstorlek, Color col, GameObject go) {
        this.gp = gp;
        for (int i = 0; i < antalPartiklar; i++) {
            float psize = (float) (Math.random() * maxPartikelstorlek + 1.0);
            Particle p = new Particle(gp, x, y, col,makeRandomPolygon((int)(Math.random()*2+5)),go);
            partiklar.add(p);
        }
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
    public void update() {
        int deadparticles = 0;
        for (Particle p : partiklar) {
            if (!p.isDead()) {
                p.update();
            } else {
                deadparticles++;
            }
        }
        if (deadparticles > partiklar.size() * 0.95) {
            dead = true;
        }
    }

    @Override
    public void render(Graphics2D g) {
        for (Particle p : partiklar) {
            if (!p.isDead()) {
                p.render(g);
            }
        }
    }

    @Override
    public boolean isDead() {
        return dead;
    }
}
